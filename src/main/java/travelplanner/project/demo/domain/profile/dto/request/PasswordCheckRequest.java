package travelplanner.project.demo.domain.profile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원정보 수정 요청 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordCheckRequest {

    @Schema(description = "비밀번호", example = "123456789")
    private String password;
}
