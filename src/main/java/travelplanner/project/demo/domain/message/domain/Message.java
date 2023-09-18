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
    private Long sendUserId;
    // 보낸 사람의 닉네임
    private String sendUserNickname;
    // 보낸 사람의 프로필 이미지
    private String sendUserProfileImg;
    // 받은 사람의 유저 인덱스
    private Long receivedUserId;
    // 받은 사람의 닉네임
    private String receivedUserNickname;
    //받은 사람의 유저 인덱스
    private String receivedUserProfileImg;
    // 메세지 내용
    private String messageContent;
    // 메세지 생성 시간
    private LocalDateTime createdAt;
}
