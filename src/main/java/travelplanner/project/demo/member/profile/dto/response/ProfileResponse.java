package travelplanner.project.demo.member.profile.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private String email;
    private String userNickname;
    private String profileImgUrl;
    private Boolean checkUser;
}
