package travelplanner.project.demo.member.profile.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "프로필 수정 응답 DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateResponse {

    @Schema(description = "유저 닉네임", example = "유저1")
    private String userNickname;

    @Schema(description = "프로필 이미지 url")
    private String profileImgUrl;
}
