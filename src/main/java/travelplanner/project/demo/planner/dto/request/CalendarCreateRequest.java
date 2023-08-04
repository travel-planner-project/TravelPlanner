package travelplanner.project.demo.planner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "날짜 생성 요청 DTO")
@Data
@NoArgsConstructor
public class CalendarCreateRequest {

    @Schema(description = "날짜", example = "7/14")
    // 사용자가 정한 날짜, Formatter 추가해야함.
    private String eachDate;

}
