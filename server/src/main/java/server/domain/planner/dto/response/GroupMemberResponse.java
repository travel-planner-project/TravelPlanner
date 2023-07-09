package server.domain.planner.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import server.domain.planner.domain.travelGroup.GroupMember;
import server.domain.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GroupMemberResponse {

    private Long groupMemberId;

    private User user;

    public GroupMemberResponse(GroupMember groupMember) {

        this.groupMemberId = groupMember.getGroupMemberId();
        this.user = groupMember.getUser();
    }

    public static List<GroupMemberResponse> groupMemberResponses (List<GroupMember> entityList) {
        return entityList.stream().map(GroupMemberResponse::new).collect(Collectors.toList());
    }
}
