package server.domain.message.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Message {

    // 메세지 인덱스
    @Id
    private Long messageId;

    //  보낸 유저
    private String sendUserId;

    // 수신 유저
    private String receivedUserId;

    // 보낸 일시
    @Column(columnDefinition = "timestamp default now()")
    private LocalDateTime sendAt;

    // 메세지 내용
    private String messageContent;

    // 읽음 여부
    @ColumnDefault("false")
    private Boolean readCheck;
}
