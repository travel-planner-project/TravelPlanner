package travelplanner.project.demo.planner.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelplanner.project.demo.planner.domain.Planner;

@Data
@NoArgsConstructor
public class PlannerDetailResponse {

    private Long plannerId;

    private String planTitle;

    private Boolean isPrivate;

    //to - do 추가

    public PlannerDetailResponse(Planner entity){
        this.plannerId = entity.getPlannerId();
        this.planTitle = entity.getPlanTitle();
        this.isPrivate = entity.getIsPrivate();

    }


}
