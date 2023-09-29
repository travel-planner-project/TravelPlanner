package travelplanner.project.demo.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Comment", description = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 리스트 조회
    @Operation(summary = "댓글 리스트 조회", description = "특정 게시글의 댓글 목록을 페이지 단위로 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 리스트 조회 성공",
                    content = @Content(schema = @Schema(implementation = PageUtil.class))),
            @ApiResponse(responseCode = "404", description = "댓글이나 게시글을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @GetMapping
    public PageUtil<CommentResponse> getCommentList(
            @Parameter(description = "페이지 번호(0부터 시작), 기본값 0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기, 기본값 6") @RequestParam(defaultValue = "6") int size,
            @Parameter(description = "게시글 ID") @RequestParam(required = false) Long postId) {

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

    @Operation(summary = "댓글 삭제", description = "특정 게시글의 특정 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한이 부족하여 접근할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "댓글 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류로 인해 요청을 처리할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @DeleteMapping("/{postId}/{commentId}")
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId, HttpServletRequest request) {
        commentService.deleteComment(postId, commentId, request);
    }

    @Operation(summary = "댓글 생성", description = "특정 게시글에 새로운 댓글을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "입력하지 않은 요소가 존재합니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping("/{postId}")
    public CommentResponse createComment(@PathVariable Long postId,
            @RequestBody CommentCreateRequest commentCreateRequest) {
        CommentResponse createCommentResponse = commentService.createComment(postId, commentCreateRequest);

        return createCommentResponse;
    }


    @Operation(summary = "댓글 수정", description = "댓글의 내용을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "댓글이나 게시글을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 부족하여 접근할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PatchMapping("/{postId}/{commentId}")
    public ResponseEntity updateComment(
            @Parameter(description = "게시글 ID", required = true) @PathVariable Long postId,
            @Parameter(description = "댓글 ID", required = true) @PathVariable Long commentId,
            @Parameter(description = "수정할 댓글 정보", required = true) @RequestBody CommentEditRequest commentEditRequest,
            HttpServletRequest request
    ) {
        commentService.editComment(postId, commentId, commentEditRequest, request);

        return ResponseEntity.ok().body("댓글 수정 성공");
    }
}
