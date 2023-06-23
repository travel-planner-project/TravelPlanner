package server.domain.planner.dto.response;

import server.domain.planner.domain.Planner;
import server.domain.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class PlannerListResponse {

    // 유저 이름
    private String userNickname;

    // 플래너 인덱스
    private Long plannerId;

    // 플래너 제목
    private String planTitle;


    public PlannerListResponse(Planner entity) {

        this.plannerId = entity.getPlannerId();
        this.planTitle = entity.getPlanTitle();
        this.userNickname = entity.getUserNickname();
    }

    //  플래너 리스트
    public static List<PlannerListResponse> plannerListResponses (List<Planner> entityList) {

        return entityList.stream().map(PlannerListResponse::new).collect(Collectors.toList());
    }
}
