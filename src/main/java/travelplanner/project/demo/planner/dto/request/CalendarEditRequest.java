package travelplanner.project.demo.planner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "날짜 수정 요청 DTO")
@Getter
@Builder
@AllArgsConstructor
public class CalendarEditRequest {

    @Schema(description = "날짜 인덱스", example = "1")
    private Long dateId;

    @Schema(description = "날짜", example = "7/14")
    private String dateTitle;
}
