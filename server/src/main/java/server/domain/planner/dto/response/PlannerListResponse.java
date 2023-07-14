package server.domain.planner.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiModelProperty;
import server.domain.planner.domain.Planner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PlannerListResponse {

    @ApiModelProperty(example = "1")
    private Long userId;

    @ApiModelProperty(example = "1")
    private Long plannerId;

    @ApiModelProperty(example = "플래너 제목")
    private String planTitle;

    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public PlannerListResponse(Planner entity) {

        this.plannerId = entity.getPlannerId();
        this.planTitle = entity.getPlanTitle();
        this.userId = entity.getUserId();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
    }

    //  플래너 리스트
    public static List<PlannerListResponse> plannerListResponses (List<Planner> entityList) {

        return entityList.stream().map(PlannerListResponse::new).collect(Collectors.toList());
    }
}
