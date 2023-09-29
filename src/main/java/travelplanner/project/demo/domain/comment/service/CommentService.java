package travelplanner.project.demo.domain.comment.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.domain.comment.domain.Comment;
import travelplanner.project.demo.domain.comment.domain.CommentEditor;
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
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final AuthUtil authUtil;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PageUtil<CommentResponse> getCommentList(Long postId, Pageable pageable) {

        validatePost(postId);

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

        boolean parentIsNull = commentCreateRequest.getParentId() == null;
        Post post = validatePost(postId);

        Comment comment = null;

        if (parentIsNull) {
            comment = Comment.builder()
                    .post(post)
                    .commentContent(commentCreateRequest.getCommentContent())
                    .build();
        }

        if (!parentIsNull) {
            Long parentId = commentCreateRequest.getParentId();
            Comment findParent = validateComment(parentId);

            comment = Comment.builder()
                    .post(post)
                    .commentContent(commentCreateRequest.getCommentContent())
                    .parent(findParent)
                    .build();
        }

        commentRepository.save(comment);

        CommentResponse.CommentResponseBuilder commentResponseBuilder = CommentResponse.builder()
                .postId(post.getId())
                .commentId(comment.getId())
                .commentContent(comment.getCommentContent());

        CommentResponse commentResponse = null;
        if(parentIsNull){
            commentResponse = commentResponseBuilder
                    .parentId(null)
                    .build();
        }

        if (!parentIsNull) {
            commentResponse = commentResponseBuilder
                    .parentId(commentCreateRequest.getParentId())
                    .build();
        }

        return commentResponse;
    }

    @Transactional
    public void editComment(Long postId, Long commentId,
                                       CommentEditRequest commentEditRequest,
                                       HttpServletRequest request) {
        validatePost(postId);
        Comment comment = validateComment(commentId);

        Member currentMember = authUtil.getCurrentMember(request);

        validateCommentAccess(comment, currentMember);

        CommentEditor.CommentEditorBuilder editorBuilder = comment.toEditor();
        log.info("flag 1 commentEditRequest.getCommentContent() = " + commentEditRequest.getCommentContent());

        CommentEditor commentEditor = editorBuilder
                .commentContent(commentEditRequest.getCommentContent())
                .build();
        log.info("flag 2 commentEditor.getCommentContent() = " + commentEditor.getCommentContent());

        comment.edit(commentEditor);
        log.info("flag 3 comment.getCommentContent() = " + comment.getCommentContent());
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, HttpServletRequest request) {

        validatePost(postId);
        Comment comment = validateComment(commentId);

        Member currentMember = authUtil.getCurrentMember(request);

        validateCommentAccess(comment, currentMember);

        commentRepository.delete(comment);
    }

    // Post 존재 여부 검증
    public Post validatePost(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType.POST_NOT_FOUND));
    }

    // Comment 존재 여부 검증
    public Comment validateComment(Long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorType.COMMENT_NOT_FOUND));
    }

    // 작성자와 현재 로그인한 유저가 다를 경우
    public void validateCommentAccess(Comment comment, Member currentMember) {
        if (comment.getPost().getMember() != currentMember) {
            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
        }
    }
}
