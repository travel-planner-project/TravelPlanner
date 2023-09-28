package travelplanner.project.demo.domain.post.post.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.post.image.domain.Image;
import travelplanner.project.demo.domain.post.image.repository.ImageRepository;
import travelplanner.project.demo.domain.post.post.domain.Post;
import travelplanner.project.demo.domain.post.post.dto.request.PostCreateRequest;
import travelplanner.project.demo.domain.post.post.dto.request.PostDeleteRequest;
import travelplanner.project.demo.domain.post.post.dto.request.PostUpdateRequest;
import travelplanner.project.demo.domain.post.post.dto.response.PostDetailResponse;
import travelplanner.project.demo.domain.post.post.dto.response.PostListResponse;
import travelplanner.project.demo.domain.post.post.repository.PostRepository;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ApiExceptionResponse;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.AuthUtil;
import travelplanner.project.demo.global.util.PageUtil;
import travelplanner.project.demo.global.util.S3Util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final S3Util s3Util;
    private final AuthUtil authUtil;
    // 포스트 리스트
    public PageUtil<PostListResponse> getPostList(Pageable pageable, HttpServletRequest request) {

        // 최신 피드
        Page<Post> page = postRepository.findAllByOrderByCreatedAtDesc(pageable);

        List<PostListResponse> postListResponses = page.getContent()
                .stream()
                .map(post -> new PostListResponse(
                        post.getId(),
                        post.getPostTitle(),
                        post.getCreatedAt(),
                        post.getImages().stream()
                                .filter(image -> image.getIsThumbnail())
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return new PageUtil<>(postListResponses, pageable, page.getTotalPages());
    }


    @Transactional
    public void deletePost(HttpServletRequest request, PostDeleteRequest postDeleteRequest) {
        Long postId = postDeleteRequest.getPostId();

        //포스트가 존재하지 않을 경우
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType.POST_NOT_FOUND));


        Member currentMember = authUtil.getCurrentMember(request);

        // 포스트를 생성한 사람이 삭제
        if(post.getMember().getId().equals(currentMember.getId())){

            postRepository.delete(post);

        }else{
            // 포스트를 생성한 사람과 현재 유저가 다름 -> 권한 없음
            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
        }
    }

    public PostDetailResponse getPostDetailById(Long postId, HttpServletRequest request) {
        Optional<Post> post = postRepository.findById(postId);
        // 댓글 추가
        return PostDetailResponse.builder()
                .postId(post.get().getId())
                .postTitle(post.get().getPostTitle())
                .postContent(post.get().getPostContent())
                .createdAt(post.get().getCreatedAt())
                .images(post.get().getImages())
                .build();
    }

    @Transactional(readOnly = false)
    public ResponseEntity<?> createPost(HttpServletRequest request, List<MultipartFile> fileList, PostCreateRequest postCreateRequest) throws IOException {
        //log.info("------------포스트 생성 들어오나?");
        Member member = authUtil.getCurrentMember(request);

        //포스트 등록
        Post createPost = Post.builder()
                .postTitle(postCreateRequest.getPostTitle())
                .postContent(postCreateRequest.getPostContent())
                .createdAt(LocalDateTime.now())
                .member(member)
                .build();

        postRepository.save(createPost);


        // 파일 있는 경우 세팅
        if(!fileList.isEmpty()){
            Long rank=1L;
            List<Image> imageList = new ArrayList<>();
            for(MultipartFile multipartFile : fileList){

                String originalImgName = multipartFile.getOriginalFilename();
                String uniqueImgName = s3Util.generateUniqueImgName(originalImgName, member.getId());

                //파일을 디렉토리에 저장
                String localFilePath = System.getProperty("java.io.tmpdir")+ "/" + uniqueImgName;
                multipartFile.transferTo(Paths.get(localFilePath));

                //s3 이미지 업로드
                s3Util.uploadFile(uniqueImgName, localFilePath, "upload/post/");
                String imgUrl = "https://travel-planner-buckets.s3.ap-northeast-2.amazonaws.com/upload/post/";

                //파일 객체 생성
                Image image = Image.builder()
                        .postImgUrl(imgUrl+uniqueImgName).keyName(uniqueImgName)
                        .sort(rank)
                        .isThumbnail(false)
                        .post(createPost)
                        .build();

                //이미지 리스트에 추가
                imageList.add(image);
                rank++;
                
                //임시 파일 삭제
                s3Util.deleteLocalFile(localFilePath);
            }
            imageRepository.saveAll(imageList);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(ErrorType.CREATED, headers, HttpStatus.OK);
    }

    @Transactional(readOnly = false)
    public ResponseEntity<?> updatePost(HttpServletRequest request, List<MultipartFile> fileList, PostUpdateRequest postUpdateRequest) throws IOException {

        Member member = authUtil.getCurrentMember(request);

        // 포스트가 존재하지 않을 경우
        Optional<Post> post = Optional.ofNullable(postRepository.findById(postUpdateRequest.getPostId())
                .orElseThrow(() -> new ApiException(ErrorType.POST_NOT_FOUND)));

        // 포스트를 생성한 사람이 아닐 경우
        if(!post.get().getMember().getId().equals(member.getId())){
            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
        }

        Post postUpdate = Post.builder()
                .postTitle(postUpdateRequest.getPostTitle())
                .postContent(postUpdateRequest.getPostContent())
                .build();

        // 파일이 존재하는 경우
        if(!fileList.isEmpty()){
            Long rank=1L;
            List<Image> imageList = new ArrayList<>();
            for(MultipartFile multipartFile : fileList){

                //기존 이미지 삭제
                List<Image> images = imageRepository.findAllById(Collections.singleton(postUpdateRequest.getPostId()));
                for(Image image : images){
                    String key = image.getKeyName();
                    s3Util.deleteFile(key, "upload/post/");
                }

                imageRepository.deleteAllByPostId(postUpdateRequest.getPostId());

                //수정 이미지 등록
                String originalImgName = multipartFile.getOriginalFilename();
                String uniqueImgName = s3Util.generateUniqueImgName(originalImgName, member.getId());

                //파일을 디렉토리에 저장
                String localFilePath = System.getProperty("java.io.tmpdir")+ "/" + uniqueImgName;
                multipartFile.transferTo(Paths.get(localFilePath));

                //s3 이미지 업로드
                s3Util.uploadFile(uniqueImgName, localFilePath, "upload/post/");
                String imgUrl = "https://travel-planner-buckets.s3.ap-northeast-2.amazonaws.com/upload/post/";

                //파일 객체 생성
                Image image = Image.builder()
                        .postImgUrl(imgUrl+uniqueImgName).keyName(uniqueImgName)
                        .sort(rank)
                        .isThumbnail(false)
                        .post(post.get())
                        .build();

                //이미지 리스트에 추가
                imageList.add(image);
                rank++;
                
                // 임시 파일 삭제
                s3Util.deleteLocalFile(localFilePath);
            }
            imageRepository.saveAll(imageList);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
