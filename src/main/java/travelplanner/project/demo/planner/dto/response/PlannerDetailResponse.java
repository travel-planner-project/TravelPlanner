package travelplanner.project.demo.planner.dto.response;

import lombok.*;
import travelplanner.project.demo.planner.domain.Planner;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlannerDetailResponse {

    private Long plannerId;

    private String planTitle;

    private Boolean isPrivate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
