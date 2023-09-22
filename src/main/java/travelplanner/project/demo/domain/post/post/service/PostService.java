package travelplanner.project.demo.domain.post.post.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.post.post.domain.Post;
import travelplanner.project.demo.domain.post.post.dto.request.PostDeleteRequest;
import travelplanner.project.demo.domain.post.post.dto.response.PostListResponse;
import travelplanner.project.demo.domain.post.post.repository.PostRepository;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.AuthUtil;
import travelplanner.project.demo.global.util.PageUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final AuthUtil authUtil;
    // 포스트 리스트
    public PageUtil<PostListResponse> getPostList(Pageable pageable, HttpServletRequest request) {

        // 최신 피드
        Page<Post> page = postRepository.findAll(pageable, Sort.by(Sort.Direction.DESC, "createdAt"));

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
}
