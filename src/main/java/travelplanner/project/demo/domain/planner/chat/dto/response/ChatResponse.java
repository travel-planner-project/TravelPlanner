package travelplanner.project.demo.domain.planner.chat.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class ChatResponse {

    private Long id;
    private Long userId;
    private String userNickname;
    private String profileImgUrl;
    private String message;
}

