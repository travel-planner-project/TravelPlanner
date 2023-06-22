package server.domain.message.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.user.domain.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MessageRoom {

    // 메세지 방 인덱스
    @Id
    private Long messageRoomId;

    // 메세지 방 유저
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    // 메세지
    @OneToMany
    private List<Message> messages = new ArrayList<>();
}
