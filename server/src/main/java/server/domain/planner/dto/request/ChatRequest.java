package server.domain.planner.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRequest {

    private String message;
    private Long userId;
}
