package server.domain.planner.dto.request.todo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GeneralTodoUpdateRequest {

    private Long todoId;
    private String todoTitle;
    private LocalDateTime todoDate;
    private String todoContent;
    private Boolean isPrivate;
}
