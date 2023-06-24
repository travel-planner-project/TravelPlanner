package server.domain.planner.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import server.domain.planner.dto.request.PlannerCreateRequest;
import server.domain.planner.dto.request.PlannerUpdateRequest;
import server.domain.planner.service.PlannerService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/planner")
@AllArgsConstructor
public class PlannerController {

    private final PlannerService plannerService;


    // 플래너 리스트 뷰
    @GetMapping(value = "")
    @ResponseBody
    public Map<String, Object> getPlannerList(
            @RequestParam String userNickname, Pageable pageable
    ) {
        return plannerService.findPlannerListByUserNickname(userNickname, pageable);
    }

    // 플래너 추가
    @PostMapping(value = "")
    public void createPlanner (
            @RequestBody PlannerCreateRequest request
    ) {
        plannerService.createPlanner(request);

        System.out.println("요청 " + request);
    }

    // 플래너 수정
    @PatchMapping(value = "")
    public void updatePlanner (
            @RequestBody PlannerUpdateRequest request
    ) {
        plannerService.updatePlanner(request);

        System.out.println("수정 " + request);
    }

    // 플래너 삭제
    @DeleteMapping(value = "")
    public void deletePlanner (
            @RequestParam Long plannerId
    ) {
        plannerService.deletePlanner(plannerId);
    }
}
