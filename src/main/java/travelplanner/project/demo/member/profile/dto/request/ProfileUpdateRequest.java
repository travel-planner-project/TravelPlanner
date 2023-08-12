package travelplanner.project.demo.member.profile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "프로필 수정 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequest {

    @Schema(description = "유저 닉네임", example = "유저1")
    private String userNickname;

    @Schema(description = "이미지 변경 여부", example = "false")
    private Boolean changeProfileImg;
}
