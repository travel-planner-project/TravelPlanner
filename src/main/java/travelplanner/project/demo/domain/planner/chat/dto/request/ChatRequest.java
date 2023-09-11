package travelplanner.project.demo.domain.planner.chat.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ChatRequest {

    private Long userId;
    private String message;
}
