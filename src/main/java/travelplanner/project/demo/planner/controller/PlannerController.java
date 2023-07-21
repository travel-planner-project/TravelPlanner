package travelplanner.project.demo.planner.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travelplanner.project.demo.planner.dto.response.PlannerListResponse;
import travelplanner.project.demo.planner.service.PlannerService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/planner")
@AllArgsConstructor
public class PlannerController {

    private final PlannerService plannerService;

    //플래너 리스트 조회
    public Page<PlannerListResponse> getPlannerList(@RequestParam Long userId, final Pageable pageable){
            return plannerService.findPlannerListByUserId(userId, pageable).map(PlannerListResponse::new);
    }

    //플래너 삭제
    public void deletePlanner(@RequestParam Long plannerId, HttpServletRequest request){
            plannerService.deletePlanner(plannerId, request);
    }
}
