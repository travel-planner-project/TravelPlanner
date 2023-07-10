package server.domain.planner.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.domain.planner.domain.travelGroup.GroupMemberType;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class PlannerCreateRequest {

    @ApiModelProperty(example = "1")
    private Long userId;

    @ApiModelProperty(example = "플래너 제목")
    @NotEmpty
    private String planTitle;

    @ApiModelProperty(example = "false")
    private Boolean isPrivate;
}
