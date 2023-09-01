package travelplanner.project.demo.planner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "그룹멤버 검색 요청 DTO")
@Getter
public class GroupMemberSearchRequest {

    @Schema(description = "이메일", example = "user1@gmail.com")
    private String email;
}
