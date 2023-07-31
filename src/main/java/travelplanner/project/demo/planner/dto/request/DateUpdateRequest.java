package travelplanner.project.demo.planner.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DateUpdateRequest {

    private Long dateId;
    private String eachDate;
}
