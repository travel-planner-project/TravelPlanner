package server.domain.planner.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PlannerUpdateRequest {

    @ApiModelProperty(example = "1")
    private Long plannerId;

    @ApiModelProperty(example = "플래너 제목")
    private String planTitle;

    @ApiModelProperty(example = "false")
    private Boolean isPrivate;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
