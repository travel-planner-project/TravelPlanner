package travelplanner.project.demo.planner.dto.request;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PlannerUpdateRequest {

    private Long plannerId;

    private String planTitle;

    private Boolean isPrivate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
