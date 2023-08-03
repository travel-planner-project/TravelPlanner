package travelplanner.project.demo.planner.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.planner.dto.request.PlannerCreateRequest;
import travelplanner.project.demo.planner.dto.request.PlannerUpdateRequest;
import travelplanner.project.demo.planner.service.PlannerService;


@Tag(name = "Planner", description = "플래너 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/planner")
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
    public void deletePlanner(@RequestParam Long plannerId) {
        plannerService.deletePlanner(plannerId);
    }

    @PostMapping
    public ResponseEntity createPlanner(
            @RequestBody @Validated PlannerCreateRequest request, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Planner 생성에 실패했습니다. Invalid request입니다. ");
        }

        plannerService.createPlanner(request);
        return ResponseEntity.ok().body("Planner가 정상적으로 생성되었습니다. ");
    }

    @PatchMapping
    public ResponseEntity updatePlanner(
            @RequestBody @Validated PlannerUpdateRequest request, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Planner 업데이트 실패했습니다. Invalid request입니다. ");
        }

        plannerService.updatePlanner(request.getPlannerId(), request);
        return ResponseEntity.ok().body("정상적으로 수정되었습니다.");
    }
}
