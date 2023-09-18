package travelplanner.project.demo.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.domain.planner.planner.dto.request.PlannerCreateRequest;
import travelplanner.project.demo.domain.planner.planner.dto.request.PlannerDeleteRequest;
import travelplanner.project.demo.domain.planner.planner.dto.response.PlannerCreateResponse;
import travelplanner.project.demo.domain.planner.planner.dto.response.PlannerDetailResponse;
import travelplanner.project.demo.domain.planner.planner.dto.response.PlannerListResponse;
import travelplanner.project.demo.domain.planner.planner.editor.PlannerEditRequest;
import travelplanner.project.demo.global.exception.ApiExceptionResponse;

@Tag(name = "Post", description = "포스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {


    @Operation(summary = "포스트 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "포스트 리스트 조회 성공"),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @GetMapping
    public Page<PlannerListResponse> getPostList(
            Pageable pageable,
            HttpServletRequest request) {
        return null;
    }

    @Operation(summary = "포스트 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "포스트 상세 조회 성공"),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @GetMapping("/{postId}")
    public PlannerDetailResponse getPlannerDetail(@PathVariable Long plannerId, HttpServletRequest request) {
        return null;
    }

    @Operation(summary = "포스트 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "포스트 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한이 부족하여 접근할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @DeleteMapping
    //포스트 삭제
    public void deletePlanner(HttpServletRequest request, @RequestBody PlannerDeleteRequest plannerDeleteRequest) {

    }

    @Operation(summary = "포스트 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "포스트 생성 성공"),
            @ApiResponse(responseCode = "500", description = "입력하지 않은 요소가 존재합니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping
    public PlannerCreateResponse createPlanner(
            @RequestBody PlannerCreateRequest plannerCreateRequest, HttpServletRequest request) {

        return null;
    }


    @Operation(summary = "포스트 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "포스트 수정 성공"),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "입력하지 않은 요소가 존재합니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PatchMapping
    public ResponseEntity updatePlanner(
            HttpServletRequest request, @RequestBody  PlannerEditRequest plannerEditRequest) {

        return ResponseEntity.ok().body("포스트 수정 성공");
    }


}
