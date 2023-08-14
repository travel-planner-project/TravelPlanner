package travelplanner.project.demo.member.socialauth.google;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GoogleOAuthTokenRequest {

    private String clientId;
    private String redirectUri;
    private String clientSecret;
    private String responseType;
    private String code;
    private String accessType;
    private String grantType;
    private String state;
    private String includeGrantedScopes;
    private String loginHint;
    private String prompt;
}
