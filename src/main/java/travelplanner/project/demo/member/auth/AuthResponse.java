package travelplanner.project.demo.member.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 응답 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    @Schema(description = "유저 인덱스", example = "1")
    private Long userId;

    @Schema(description = "이메일", example = "user1@gmail.com")
    private String email;

    @Schema(description = "유저 닉네임", example = "유저1")
    private String userNickname;

    @Schema(description = "프로필 이미지 주소")
    private String profileImgUrl;

    @Schema(description = "로그인 유형", example = "google")
    private String provider;
}
