package travelplanner.project.demo.domain.message.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendRequest {
    private Long sendUserId;
    private Long receivedUserId;
    private String messageContent;
}
