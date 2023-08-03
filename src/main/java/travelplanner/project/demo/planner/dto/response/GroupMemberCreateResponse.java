package travelplanner.project.demo.planner.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupMemberCreateResponse {

    private Long groupMemberId;
    private String nickname;
    private String profileImageUrl;
    private String role;
}
