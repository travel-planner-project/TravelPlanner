package server.domain.planner.plan.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DateUpdateRequest {

    private Long dateId;
    private String dateTitle;
}
