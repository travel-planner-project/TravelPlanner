package travelplanner.project.demo.member.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 요청 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Schema(description = "이메일", example = "user1@gmail.com")
    private String email;

    @Schema(description = "비밀번호", example = "123456789")
    private String password;
}
