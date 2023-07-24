package travelplanner.project.demo.planner.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import travelplanner.project.demo.planner.domain.Planner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PlannerListResponse {

    private Long plannerId;
    private Long userId;


    private String planTitle;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;

    //플래너 객체
    public PlannerListResponse(Planner planner){
        this.plannerId = planner.getPlannerId();
        this.planTitle = planner.getPlanTitle();
        this.userId = planner.getMember().getUserId();
        this.startDate = planner.getStartDate();
        this.endDate = planner.getEndDate();
    }
    
    //플래너 리스트
    public static List<PlannerListResponse> plannerListResponse (List<Planner> entityList){
        return entityList.stream().map(PlannerListResponse::new).collect(Collectors.toList());
    }
}
