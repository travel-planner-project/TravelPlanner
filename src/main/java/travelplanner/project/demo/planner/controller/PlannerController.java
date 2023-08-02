package travelplanner.project.demo.planner.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.member.profile.dto.response.ProfileResponse;
import travelplanner.project.demo.planner.dto.request.PlannerCreateRequest;
import travelplanner.project.demo.planner.dto.request.PlannerDeleteRequest;
import travelplanner.project.demo.planner.dto.request.PlannerUpdateRequest;
import travelplanner.project.demo.planner.dto.response.PlannerDetailResponse;
import travelplanner.project.demo.planner.dto.response.PlannerListResponse;
import travelplanner.project.demo.planner.service.PlannerService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/planner")
@AllArgsConstructor
public class PlannerController {

    private final PlannerService plannerService;

    @Operation(summary = "플래너 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특정 플래너 접근 성공",
                    content = @Content(schema = @Schema(implementation = PlannerDetailResponse.class))),
            @ApiResponse(responseCode = "404", description = "특정 플래너를 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @GetMapping("/{plannerId}")
    public ResponseEntity<PlannerDetailResponse> findByPlannerId(
            @Parameter(name = "plannerId", description = "플래너 인덱스", in = ParameterIn.PATH) // swagger
            @PathVariable Long plannerId ) throws Exception{
        return ResponseEntity.ok(plannerService.getDetailPlanner(plannerId));
    }

    @Operation(summary = "플래너 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 플래너 접근 성공",
                    content = @Content(schema = @Schema(implementation = PlannerListResponse.class))),
            @ApiResponse(responseCode = "400", description = "플래너 조회에 실패한 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @GetMapping
    public ResponseEntity<Page<PlannerListResponse>> findByPlannerList(
            @Parameter(description = "페이지 정보", in = ParameterIn.QUERY) // swagger
            Pageable pageable){
        return ResponseEntity.ok(plannerService.getPlannerListByUserId(pageable));
    }

    @Operation(summary = "플래너 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "플래너가 정상적으로 삭제되었습니다."),

            @ApiResponse(responseCode = "400", description = "플래너 삭제에 실패한 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),

            @ApiResponse(responseCode = "403", description = "플래너를 생성한 유저가 아닌 경우",
            content = @Content(schema = @Schema(implementation = ApiException.class))),

            @ApiResponse(responseCode = "404", description = "삭제하려는 플래너가 없을 경우",
            content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @DeleteMapping
    public void deletePlanner(@RequestBody PlannerDeleteRequest request) {
        plannerService.deletePlanner(request);
    }

    @Operation(summary = "플래너 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "플래너가 정상적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "플래너 생성에 실패한 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @PostMapping
    public void createPlanner(@RequestBody PlannerCreateRequest request) {
        plannerService.createPlanner(request);
    }


    // 플래너 수정

    @Operation(summary = "플래너 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "플래너가 정상적으로 수정되었습니다."),
            @ApiResponse(responseCode = "400", description = "플래너 수정에 실패한 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "403", description = "플래너를 생성한 유저가 아닌 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "404", description = "수정하려는 플래너가 없을 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @PatchMapping
    public void updatePlanner(
            @RequestBody PlannerUpdateRequest request) {
        plannerService.updatePlanner(request);
    }
}
