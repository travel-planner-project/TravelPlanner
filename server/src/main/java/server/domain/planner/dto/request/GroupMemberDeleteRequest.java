package server.domain.planner.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupMemberDeleteRequest {

    private Long plannerId;
    private Long userId;
}
