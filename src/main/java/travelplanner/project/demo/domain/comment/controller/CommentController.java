package travelplanner.project.demo.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.domain.comment.dto.request.CommentCreateRequest;
import travelplanner.project.demo.domain.comment.dto.request.CommentEditRequest;
import travelplanner.project.demo.domain.comment.dto.response.CommentResponse;
import travelplanner.project.demo.domain.comment.service.CommentService;
import travelplanner.project.demo.global.exception.ApiExceptionResponse;
import travelplanner.project.demo.global.util.PageUtil;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 리스트 조회 성공"),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "댓글 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @GetMapping
    public PageUtil<CommentResponse> getCommentList(
            @Parameter(name="page", description = "몇번째 페이지(0부터 시작), 기본값 0", in = ParameterIn.QUERY)
            @RequestParam(defaultValue = "0") int page,

            @Parameter(name = "size", description = "희망 플래너 갯수, 기본값 6", in = ParameterIn.QUERY) // swagger
            @RequestParam(defaultValue = "6") int size,

            @Parameter(name = "postId", description = "게시글의 index", in = ParameterIn.QUERY)
            @RequestParam(required = false) Long postId){


        PageUtil<CommentResponse> commentList = commentService.getCommentList(postId, PageRequest.of(page, size));

        return commentList;
    }

//    @Operation(summary = "댓글 상세 조회")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "댓글 상세 조회 성공"),
//            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없습니다.",
//                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
//            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다.",
//                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
//    })
//    @GetMapping("/{commentId}")
//    public CommentResponse getCommentDetail(@PathVariable Long commentId) {
//        return null;
//    }

    @Operation(summary = "댓글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한이 부족하여 접근할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "댓글 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @DeleteMapping
    //포스트 삭제
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId, HttpServletRequest request) {
        commentService.deleteComment(postId, commentId, request);
    }

    @Operation(summary = "댓글 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 생성 성공"),
            @ApiResponse(responseCode = "500", description = "입력하지 않은 요소가 존재합니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping
    public CommentResponse createComment(@PathVariable Long postId,
            @RequestBody CommentCreateRequest commentCreateRequest) {
        CommentResponse createCommentResponse = commentService.createComment(postId, commentCreateRequest);

        return createCommentResponse;
    }


    @Operation(summary = "댓글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "입력하지 않은 요소가 존재합니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PatchMapping
    public ResponseEntity updateComment(@PathVariable Long postId, @PathVariable Long commentId,
                                        @RequestBody CommentEditRequest commentEditRequest) {

        commentService.editComment(postId, commentId, commentEditRequest);

        return ResponseEntity.ok().body("댓글 수정 성공");
    }
}
