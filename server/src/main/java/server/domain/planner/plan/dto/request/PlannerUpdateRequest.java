package server.domain.planner.plan.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlannerUpdateRequest {

    // 플래너 인덱스
    private Long plannerId;

    // 플래너 수정 제목
    private String planTitle;

    // 플래너 공개 여부
    private Boolean isPrivate;
}
