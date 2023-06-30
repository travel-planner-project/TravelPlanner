package server.domain.user.dto;

import lombok.Data;

import javax.persistence.PrePersist;

@Data
public class UserRequestDto {

    private String email;

    private String password;

    private String passwordCheck;

    private String userNickname;
}
