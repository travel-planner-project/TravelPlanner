package travelplanner.project.demo.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.domain.comment.dto.request.CommentCreateRequest;
import travelplanner.project.demo.domain.comment.dto.request.CommentEditRequest;
import travelplanner.project.demo.domain.comment.dto.response.CommentResponse;
import travelplanner.project.demo.domain.comment.dto.response.CommentDetailResponse;
import travelplanner.project.demo.domain.comment.dto.response.CommentListResponse;
import travelplanner.project.demo.domain.comment.repository.CommentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<CommentListResponse> getCommentList(Long postId) {
        return null;
    }

    public CommentDetailResponse getCommentDetail(Long postId, Long commentId) {
        return null;
    }

    public CommentResponse createComment(Long postId, CommentCreateRequest commentCreateRequest) {
        return null;
    }

    @Transactional
    public CommentResponse editComment(Long postId, Long commentId, CommentEditRequest commentEditRequest) {
        return null;
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {

    }
}
