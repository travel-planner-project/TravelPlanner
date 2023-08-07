package travelplanner.project.demo.planner.chat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRequest {

    private Long userId;
    private String message;
}
