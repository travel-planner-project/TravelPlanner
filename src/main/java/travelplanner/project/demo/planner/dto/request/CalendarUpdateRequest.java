package travelplanner.project.demo.planner.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CalendarUpdateRequest {

    private Long dateId;
    private String eachDate;
}
