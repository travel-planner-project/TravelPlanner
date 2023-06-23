package server.domain.planner.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class DateCreateRequest {

    @NotEmpty
    private String dateTitle;
}
