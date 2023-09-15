package travelplanner.project.demo.domain.planner.groupmember.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "그룹멤버 추가 요청 DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMemberCreateRequest {

    @Schema(description = "유저아이디", example = "1")
    private Long userId;
}