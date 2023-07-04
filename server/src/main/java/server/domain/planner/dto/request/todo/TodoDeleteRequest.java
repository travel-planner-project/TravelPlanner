package server.domain.planner.dto.request.todo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoDeleteRequest {

    private Long todoId;
}
