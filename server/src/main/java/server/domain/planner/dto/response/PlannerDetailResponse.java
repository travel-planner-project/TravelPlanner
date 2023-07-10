package server.domain.planner.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.domain.planner.domain.Planner;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PlannerDetailResponse {

    @ApiModelProperty(example = "1")
    private Long plannerId;

    @ApiModelProperty(example = "플래너 제목")
    private String planTitle;

    @ApiModelProperty(example = "false")
    private Boolean isPrivate;

    // TODO 리스트
    private List<DateResponse> dateResponses = new ArrayList<>();


    public PlannerDetailResponse(Planner entity) {

        this.plannerId = entity.getPlannerId();
        this.planTitle = entity.getPlanTitle();
        this.isPrivate = entity.getIsPrivate();
        this.dateResponses = DateResponse.dateResponses(entity.getDates());
    }
}
