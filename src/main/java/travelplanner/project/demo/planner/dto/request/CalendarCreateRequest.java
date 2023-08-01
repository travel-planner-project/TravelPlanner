package travelplanner.project.demo.planner.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CalendarCreateRequest {

    // 사용자가 정한 날짜, Formatter 추가해야함.
    private String eachDate;

}
