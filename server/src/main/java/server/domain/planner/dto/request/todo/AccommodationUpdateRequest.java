package server.domain.planner.dto.request.todo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AccommodationUpdateRequest {

    private Long todoId;
    private String todoTitle;
    private LocalDateTime todoDate;
    private String todoContent;
    private Boolean isPrivate;
    private String accommodationAddress;
}
