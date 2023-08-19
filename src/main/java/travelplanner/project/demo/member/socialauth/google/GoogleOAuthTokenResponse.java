package travelplanner.project.demo.member.socialauth.google;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoogleOAuthTokenResponse {

    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String scope;
    private String token_type; // Bearer 고정
    private String id_token;
}
