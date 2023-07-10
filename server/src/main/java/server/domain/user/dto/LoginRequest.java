package server.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginRequest {

    @ApiModelProperty(example = "user1@gmail.com")
    private String email;

    @ApiModelProperty(example = "123qwe!@#QWE")
    private String password;
}
