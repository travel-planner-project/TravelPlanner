package travelplanner.project.demo.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.domain.comment.request.CommentCreateRequest;
import travelplanner.project.demo.domain.comment.request.CommentDeleteRequest;
import travelplanner.project.demo.domain.comment.request.CommentEditRequest;
import travelplanner.project.demo.domain.comment.response.CommentCreateResponse;
import travelplanner.project.demo.domain.comment.response.CommentDetailResponse;
import travelplanner.project.demo.domain.comment.response.CommentListResponse;
import travelplanner.project.demo.global.exception.ApiExceptionResponse;
import travelplanner.project.demo.global.util.PageUtil;

@RestController
public class CommentController {


    @Operation(summary = "댓글 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 리스트 조회 성공"),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "댓글 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @GetMapping
    public PageUtil<CommentListResponse> getPostList() {
        return null;
    }

    @Operation(summary = "댓글 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 상세 조회 성공"),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @GetMapping("/{commentId}")
    public CommentDetailResponse getCommentDetail(@PathVariable Long commentId) {
        return null;
    }

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
    public void deletePlanner(@RequestBody CommentDeleteRequest commentDeleteRequest) {

    }

    @Operation(summary = "댓글 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 생성 성공"),
            @ApiResponse(responseCode = "500", description = "입력하지 않은 요소가 존재합니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping
    public CommentCreateResponse createPlanner(
            @RequestBody CommentCreateRequest commentCreateRequest) {
        return null;
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
    public ResponseEntity updatePlanner(@RequestBody CommentEditRequest commentEditRequest) {

        return ResponseEntity.ok().body("댓글 수정 성공");
    }
}