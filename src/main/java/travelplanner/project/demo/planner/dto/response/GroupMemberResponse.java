package travelplanner.project.demo.planner.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import travelplanner.project.demo.planner.domain.GroupMemberType;

@Schema(description = "그룹멤버 생성 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class GroupMemberResponse {

    @Schema(description = "그룹멤버 인덱스", example = "1")
    private Long groupMemberId;

    @Schema(description = "유저 닉네임", example = "시니")
    private String nickname;

    @Schema(description = "프로필 이미지 주소")
    private String profileImageUrl;

    @Schema(description = "그룹 역할", example = "HOST")
    private GroupMemberType role;
}
