package server.domain.planner.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import server.domain.planner.service.PlannerService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/planner")
@AllArgsConstructor
public class PlannerController {

    private final PlannerService plannerService;


    // 플래너 리스트 뷰
    @GetMapping("")
    @ResponseBody
    public Map<String, Object> getPlannerList(
            @RequestParam String userNickname, Pageable pageable
    ) {
        return plannerService.findPlannerListByUserNickname(userNickname, pageable);
    }
}
