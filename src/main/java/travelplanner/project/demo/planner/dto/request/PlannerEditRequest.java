package travelplanner.project.demo.planner.dto.request;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlannerEditRequest {

    private Long plannerId;

    private String planTitle;

    private Boolean isPrivate;

}
