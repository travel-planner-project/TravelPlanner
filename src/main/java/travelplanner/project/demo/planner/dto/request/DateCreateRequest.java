package travelplanner.project.demo.planner.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DateCreateRequest {

    private LocalDateTime eachDate;

}