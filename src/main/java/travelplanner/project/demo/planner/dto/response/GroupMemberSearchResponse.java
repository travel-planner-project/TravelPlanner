package travelplanner.project.demo.planner.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupMemberSearchResponse {

    private String email;
    private String profileImageUrl;
    private String userNickname;
}
