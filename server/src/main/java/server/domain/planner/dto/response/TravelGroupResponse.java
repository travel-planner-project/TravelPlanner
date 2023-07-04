package server.domain.planner.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import server.domain.planner.domain.travelGroup.TravelGroup;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TravelGroupResponse {

    private Long travelGroupId;

    private List<GroupMemberResponse> groupMemberResponses = new ArrayList<>();

    public TravelGroupResponse(TravelGroup travelGroup) {

        this.travelGroupId = travelGroup.getTravelGroupId();
        this.groupMemberResponses = GroupMemberResponse.groupMemberResponses(travelGroup.getGroupMembers());
    }
}
