package travelplanner.project.demo.planner.chat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatResponse {

    private String userNickname;
    private String profileImgUrl;
    private String message;
}
