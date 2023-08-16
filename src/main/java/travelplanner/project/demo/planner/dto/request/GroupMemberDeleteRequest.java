package travelplanner.project.demo.planner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "그룹멤버 추가 요청 DTO")
@Data
@NoArgsConstructor
public class GroupMemberDeleteRequest {

    @Schema(description = "그룹멤버 인덱스")
    private Long groupMemberId;
}
