package travelplanner.project.demo.chat.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRequest {
    private String message;

    @NotEmpty
    private String email;
}
