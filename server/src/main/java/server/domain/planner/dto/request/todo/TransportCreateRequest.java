package server.domain.planner.dto.request.todo;

import lombok.Data;
import lombok.NoArgsConstructor;
import server.domain.planner.domain.Todo.TransportType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransportCreateRequest {

    private String todoTitle;
    private LocalDateTime todoDate;
    private String todoContent;
    private Boolean isPrivate;
    private String departTime;
    private String arriveTime;
    private TransportType transportType;
}
