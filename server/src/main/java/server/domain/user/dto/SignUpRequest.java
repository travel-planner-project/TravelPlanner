package server.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SignUpRequest {

    @ApiModelProperty(example = "user1@gmail.com")
    private String email;

    @ApiModelProperty(example = "123qwe!@#QWE")
    private String password;

    @ApiModelProperty(example = "123qwe!@#QWE")
    private String passwordCheck;

    @ApiModelProperty(example = "유저닉네임")
    private String userNickname;
}
