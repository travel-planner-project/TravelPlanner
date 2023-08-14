package travelplanner.project.demo.member.socialauth.google;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GoogleOAuthTokenDto {

    private String access_token;
    private Integer exprires_in;
    private String scope;
    private String token_type;
    private String id_token;
}
