package travelplanner.project.demo.planner.dto.request;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "플래너 수정 요청 DTO")
@Data
@NoArgsConstructor
public class PlannerEditRequest {

    @Schema(description = "플래너 인덱스", example = "1")
    private Long plannerId;

    @Schema(description = "플래너 제목", example = "제주도 여행")
    private String planTitle;

    @Schema(description = "공개여부", example = "false")
    private Boolean isPrivate;
}
