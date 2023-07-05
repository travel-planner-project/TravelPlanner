package server.domain.planner.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import server.domain.planner.domain.Planner;

import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PlannerListResponse {

    // 유저 이름
    private Long userId;

    // 플래너 인덱스
    private Long plannerId;

    // 플래너 제목
    private String planTitle;


    public PlannerListResponse(Planner entity) {

        this.plannerId = entity.getPlannerId();
        this.planTitle = entity.getPlanTitle();
        this.userId = entity.getUserId();
    }

    //  플래너 리스트
    public static List<PlannerListResponse> plannerListResponses (List<Planner> entityList) {

        return entityList.stream().map(PlannerListResponse::new).collect(Collectors.toList());
    }
}
