package travelplanner.project.demo.domain.comment.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.domain.comment.domain.Comment;
import travelplanner.project.demo.domain.comment.dto.request.CommentCreateRequest;
import travelplanner.project.demo.domain.comment.dto.request.CommentEditRequest;
import travelplanner.project.demo.domain.comment.dto.response.CommentResponse;
import travelplanner.project.demo.domain.comment.repository.CommentRepository;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.post.post.domain.Post;
import travelplanner.project.demo.domain.post.post.repository.PostRepository;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.AuthUtil;
import travelplanner.project.demo.global.util.PageUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final AuthUtil authUtil;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PageUtil<CommentResponse> getCommentList(Long postId, Pageable pageable) {

        postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType.POST_NOT_FOUND));

        Page<Comment> commentList = commentRepository.findByPostId(postId, pageable);


        List<CommentResponse> commentResponseList = new ArrayList<>();

        for (Comment comment : commentList.getContent()) {

            CommentResponse commentResponse = CommentResponse.builder()
                    .postId(comment.getPost().getId())
                    .commentId(comment.getId())
                    .commentContent(comment.getCommentContent())
                    .build();

            commentResponseList.add(commentResponse);
        }

        return new PageUtil<>(commentResponseList, pageable, commentList.getTotalPages());
    }

//    댓글 한개 조회
//    public CommentDetailResponse getCommentDetail(Long postId, Long commentId) {
//        return null;
//    }

    @Transactional
    public CommentResponse createComment(Long postId, CommentCreateRequest commentCreateRequest) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType.POST_NOT_FOUND));

        Comment comment = Comment.builder()
                .post(post)
                .commentContent(commentCreateRequest.getCommentContent())
                .build();

        commentRepository.save(comment);

        CommentResponse response = CommentResponse.builder()
                .postId(post.getId())
                .commentId(comment.getId())
                .commentContent(comment.getCommentContent())
                .build();

        return response;
    }

    @Transactional
    public CommentResponse editComment(Long postId, Long commentId, CommentEditRequest commentEditRequest) {
        return null;
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, HttpServletRequest request) {
        postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType.POST_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorType.COMMENT_NOT_FOUND));

        Member currentMember = authUtil.getCurrentMember(request);

        if (comment.getPost().getMember() != currentMember) {
            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
        }

        commentRepository.delete(comment);
    }
}
