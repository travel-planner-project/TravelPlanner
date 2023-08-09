package travelplanner.project.demo.planner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "날짜 수정 요청 DTO")
@Data
@NoArgsConstructor
public class CalendarEditRequest {

    @Schema(description = "날짜 인덱스", example = "1")
    private Long dateId;

    @Schema(description = "날짜", example = "7/14")
    private String dateTitle;
}
