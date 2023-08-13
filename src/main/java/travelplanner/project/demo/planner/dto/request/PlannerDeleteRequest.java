package travelplanner.project.demo.planner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "플래너 삭제 요청 DTO")
@Data
@NoArgsConstructor
public class PlannerDeleteRequest {

    @Schema(description = "플래너 인덱스", example = "1")
    private Long plannerId;
}
