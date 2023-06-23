package server.domain.planner.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import server.domain.planner.domain.Planner;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PlannerDetailResponse {

    // 플래너 인덱스
    private Long plannerId;

    // 플래너 제목
    private String planTitle;

    // 플래너 공개여부
    private Boolean isPrivate;

    // TODO 리스트
    private List<DateResponse> dateResponses = new ArrayList<>();


    public PlannerDetailResponse(Planner entity) {

        this.plannerId = entity.getPlannerId();
        this.planTitle = entity.getPlanTitle();
        this.dateResponses = DateResponse.dateResponses(entity.getDates());
    }
}
