package travelplanner.project.demo.planner.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DateCreateRequest {

    // 사용자가 정한 날짜, Formatter 추가해야함.
    private String eachDate;

}
