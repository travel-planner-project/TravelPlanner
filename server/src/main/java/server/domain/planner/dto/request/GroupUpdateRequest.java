package server.domain.planner.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import server.domain.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GroupUpdateRequest {

    private Long travelGroupId;

    private List<User> users = new ArrayList<>();
}
