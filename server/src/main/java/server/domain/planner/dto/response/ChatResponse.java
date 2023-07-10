package server.domain.planner.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatResponse {

    private String message;
    private String userNickname;
}
