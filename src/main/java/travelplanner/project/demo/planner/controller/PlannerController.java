package travelplanner.project.demo.planner.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ApiExceptionResponse;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.planner.dto.request.PlannerCreateRequest;
import travelplanner.project.demo.planner.dto.request.PlannerDeleteRequest;
import travelplanner.project.demo.planner.dto.request.PlannerEditRequest;
import travelplanner.project.demo.planner.dto.response.PlannerCreateResponse;
import travelplanner.project.demo.planner.dto.response.PlannerListResponse;
import travelplanner.project.demo.planner.dto.response.plannerdetail.PlannerDetailResponse;
import travelplanner.project.demo.planner.service.PlannerService;


@Tag(name = "Planner", description = "플래너 API")
@Slf4j
@RestController
@RequestMapping("/planner")
@AllArgsConstructor
public class PlannerController {

    private final PlannerService plannerService;


    @Operation(summary = "플래너 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "플래너 리스트 조회 성공"),
            @ApiResponse(responseCode = "403", description = "권한이 부족하여 접근할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @GetMapping
    public Page<PlannerListResponse> getPlannerList(Pageable pageable, @RequestParam(required = false) String email, HttpServletRequest request) {
        return plannerService.getPlannerListByUserIdOrEmail(pageable, email, request);
    }


    @Operation(summary = "플래너 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "플래너 리스트 조회 성공"),
            @ApiResponse(responseCode = "403", description = "권한이 부족하여 접근할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @GetMapping("/{plannerId}")
    public PlannerDetailResponse getPlannerDetail(@PathVariable Long plannerId, HttpServletRequest request) {
        return plannerService.getPlannerDetailByOrderAndEmail(plannerId, request);
    }


    @Operation(summary = "플래너 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "플래너 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한이 부족하여 접근할 수 없습니다.",
                content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "페이지를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @DeleteMapping
    //플래너 삭제
    public void deletePlanner(@RequestBody PlannerDeleteRequest request) {
        plannerService.deletePlanner(request);
    }


    @Operation(summary = "플래너 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "플래너 생성 성공"),
            @ApiResponse(responseCode = "500", description = "입력하지 않은 요소가 존재합니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping
    public PlannerCreateResponse createPlanner(
            @RequestBody @Validated PlannerCreateRequest request, BindingResult result) {

        if (result.hasErrors()) {
            System.out.println("PlannerController.createPlanner");
                throw new ApiException(ErrorType.INVALID_REQUEST);
//            return ResponseEntity.badRequest().body("Planner 생성에 실패했습니다. Invalid request입니다. ");
        }

        return plannerService.createPlanner(request);
    }


    @Operation(summary = "플래너 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "플래너 수정 성공"),
            @ApiResponse(responseCode = "422", description = "페이지를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "입력하지 않은 요소가 존재합니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PatchMapping
    public ResponseEntity updatePlanner(
            @RequestBody @Validated PlannerEditRequest request, BindingResult result) {

        if (result.hasErrors()) {
            throw  new ApiException(ErrorType.INVALID_REQUEST);
//            return ResponseEntity.badRequest().body("Planner 업데이트 실패했습니다. Invalid request입니다. ");
        }

        plannerService.updatePlanner (request);
        return ResponseEntity.ok().body("플래너 수정 성공");
    }
}
