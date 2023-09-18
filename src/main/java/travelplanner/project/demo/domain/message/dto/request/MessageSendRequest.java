package travelplanner.project.demo.domain.message.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendRequest {
    // 보낸 사람의 유저 인덱스
    private Long sendUserId;
    // 받은 사람의 유저 인덱스
    private Long receivedUserId;
    // 메세지 내용
    private String messageContent;
}
