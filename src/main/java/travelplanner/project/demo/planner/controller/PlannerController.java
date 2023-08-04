package travelplanner.project.demo.planner.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import travelplanner.project.demo.planner.service.PlannerService;


@Tag(name = "Planner", description = "플래너 API")
@Slf4j
@RestController
@RequestMapping("/planner")
@AllArgsConstructor
public class PlannerController {

    private final PlannerService plannerService;

//    @GetMapping
//    //플래너 리스트 조회
//    public Page<PlannerListResponse> getPlannerList(@RequestParam Long userId, final Pageable pageable) {
//        return plannerService.findPlannerListByUserId(userId, pageable).map(PlannerListResponse::new);
//    }

//    플래너 세부 조회
//    @GetMapping("/{id}")
//    public ResponseEntity<>

    @DeleteMapping
    //플래너 삭제
    public void deletePlanner(PlannerDeleteRequest request) {
        plannerService.deletePlanner(request);
    }



    @Operation(summary = "플래너 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "플래너 생성 성공"),
            @ApiResponse(responseCode = "500", description = "입력하지 않은 요소가 존재합니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping
    public ResponseEntity createPlanner(
            @RequestBody @Validated PlannerCreateRequest request, BindingResult result) {

        if (result.hasErrors()) {
                throw new ApiException(ErrorType.INVALID_REQUEST);
//            return ResponseEntity.badRequest().body("Planner 생성에 실패했습니다. Invalid request입니다. ");
        }

        plannerService.createPlanner(request);
        return ResponseEntity.ok().body("Planner가 정상적으로 생성되었습니다. ");
    }

    @Operation(summary = "플래너 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "플래너 수정 성공"),
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
        return ResponseEntity.ok().body("정상적으로 수정되었습니다.");
    }
}
