package server.domain.planner.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class PlannerCreateRequest {

    // 플래너 작성 유저
    private String userNickname;

    // 플래너 제목
    @NotEmpty
    private String planTitle;

    // 공개 여부
    private Boolean isPrivate;
}
