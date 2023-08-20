package travelplanner.project.demo.planner.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponse {

    private Long id;
    private Long userId;
    private String userNickname;
    private String profileImgUrl;
    private String message;
}
