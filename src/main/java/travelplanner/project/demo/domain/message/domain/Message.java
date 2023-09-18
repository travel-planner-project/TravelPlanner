package travelplanner.project.demo.domain.message.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 보낸 사람의 유저 인덱스
    private Long senderUserId;
    // 받은 사람의 유저 인덱스
    private Long receivedUserId;
    // 메세지 내용
    private String messageContent;
    // 메세지 생성 시간
    private LocalDateTime createdAt;
}
