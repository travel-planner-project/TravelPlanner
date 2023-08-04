package travelplanner.project.demo.member.profile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "비밀번호 변경 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateRequest {

    @Schema(description = "유저 인덱스", example = "1")
    private Long userId;

    @Schema(description = "비밀번호", example = "123456789")
    private String password;
}
