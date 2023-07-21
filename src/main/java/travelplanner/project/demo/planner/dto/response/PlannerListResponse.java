package travelplanner.project.demo.planner.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import travelplanner.project.demo.planner.domain.Planner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PlannerListResponse {
    
    private Long userId;
    
    private Long plannerId;
    
    private String planTitle;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    
    //플래너 객체
    public PlannerListResponse(Planner pn){
        this.plannerId = pn.getPlannerId();
        this.planTitle = pn.getPlanTitle();
        this.userId = pn.getUserId();
        this.startDate = pn.getStartDate();
        this.endDate = pn.getEndDate();
    }
    
    //플래너 리스트
    public static List<PlannerListResponse> plannerListResponse (List<Planner> entityList){
        return entityList.stream().map(PlannerListResponse::new).collect(Collectors.toList());
    }
}
