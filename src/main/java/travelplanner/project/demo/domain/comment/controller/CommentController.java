package travelplanner.project.demo.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.domain.planner.planner.dto.request.PlannerCreateRequest;
import travelplanner.project.demo.domain.planner.planner.dto.request.PlannerDeleteRequest;
import travelplanner.project.demo.domain.planner.planner.dto.response.PlannerCreateResponse;
import travelplanner.project.demo.domain.planner.planner.dto.response.PlannerDetailResponse;
import travelplanner.project.demo.domain.planner.planner.dto.response.PlannerListResponse;
import travelplanner.project.demo.domain.planner.planner.editor.PlannerEditRequest;
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
    public PageUtil<PlannerListResponse> getPostList() {
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
    public PlannerDetailResponse getCommentDetail(@PathVariable Long commentId) {
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
    public void deletePlanner(@RequestBody PlannerDeleteRequest plannerDeleteRequest) {

    }

    @Operation(summary = "댓글 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 생성 성공"),
            @ApiResponse(responseCode = "500", description = "입력하지 않은 요소가 존재합니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping
    public PlannerCreateResponse createPlanner(
            @RequestBody PlannerCreateRequest plannerCreateRequest) {
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
    public ResponseEntity updatePlanner(@RequestBody PlannerEditRequest plannerEditRequest) {

        return ResponseEntity.ok().body("댓글 수정 성공");
    }
}
