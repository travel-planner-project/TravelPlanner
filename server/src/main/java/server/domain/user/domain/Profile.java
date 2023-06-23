package server.domain.user.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Profile {

    // 프로필 인덱스
    @Id
    private Long profileId;

    // 프로필 유저
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    // 상태 메세지
    private String statusMessage;

    // 프로필 이미지
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "filePath")
//    private File file;
}
