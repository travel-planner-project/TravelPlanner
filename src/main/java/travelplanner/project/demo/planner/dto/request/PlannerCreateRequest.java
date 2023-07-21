package travelplanner.project.demo.planner.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PlannerCreateRequest {

    private Long userId;

    @NotEmpty
    private String planTitle;

    private Boolean isPrivate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
