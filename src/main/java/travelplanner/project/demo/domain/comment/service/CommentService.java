package travelplanner.project.demo.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.domain.comment.domain.Comment;
import travelplanner.project.demo.domain.comment.dto.request.CommentCreateRequest;
import travelplanner.project.demo.domain.comment.dto.request.CommentEditRequest;
import travelplanner.project.demo.domain.comment.dto.response.CommentResponse;
import travelplanner.project.demo.domain.comment.dto.response.CommentDetailResponse;
import travelplanner.project.demo.domain.comment.dto.response.CommentListResponse;
import travelplanner.project.demo.domain.comment.repository.CommentRepository;
import travelplanner.project.demo.domain.post.post.domain.Post;
import travelplanner.project.demo.domain.post.post.repository.PostRepository;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<CommentListResponse> getCommentList(Long postId) {
        return null;
    }

    public CommentDetailResponse getCommentDetail(Long postId, Long commentId) {
        return null;
    }

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
                .commentId(comment.getId())
                .postId(post.getId())
                .commentContent(comment.getCommentContent())
                .build();

        return response;
    }

    @Transactional
    public CommentResponse editComment(Long postId, Long commentId, CommentEditRequest commentEditRequest) {
        return null;
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {

    }
}
