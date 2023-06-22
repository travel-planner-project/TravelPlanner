package server.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {

    // 유저 닉네임
    private String userNickname;

    // 이메일
    @Id
    private String email;

    // 비밀번호
    private String password;

    // 유저 권한
    private String userRoles;
}
