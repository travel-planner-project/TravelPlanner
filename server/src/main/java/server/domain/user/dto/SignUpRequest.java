package server.domain.user.dto;

import lombok.Data;


@Data
public class SignUpRequest {

    private String email;

    private String password;

    private String passwordCheck;

    private String userNickname;
}
