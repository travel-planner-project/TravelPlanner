package travelplanner.project.demo.planner.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import travelplanner.project.demo.planner.domain.Calendar;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarResponse {

    private Long calendarId;
    private String eachDate;
    @CreatedDate
    private LocalDateTime createAt;
    private Long plannerId;

}
