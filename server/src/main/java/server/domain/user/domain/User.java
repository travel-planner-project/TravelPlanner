package server.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.user.dto.UserRequestDto;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Enumerated(EnumType.STRING)
    private Role userRole;


    public static User createUser (UserRequestDto request) {

        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .userNickname(request.getUserNickname())
                .userRole(Role.ROLE_MEMBER)
                .build();
    }
}
