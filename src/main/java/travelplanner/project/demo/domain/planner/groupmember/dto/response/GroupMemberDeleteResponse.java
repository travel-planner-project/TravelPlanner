package travelplanner.project.demo.domain.planner.groupmember.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "그룹멤버 삭제 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMemberDeleteResponse {

    @Schema(description = "그룹 멤버 인덱스", example = "1")
    private Long groupMemberId;
}
