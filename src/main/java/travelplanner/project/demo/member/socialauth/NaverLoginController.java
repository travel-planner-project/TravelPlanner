package travelplanner.project.demo.member.socialauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class NaverLoginController {


    @Value("${oauth.naver.clientId}")
    private String clientId; // 앱 키 중 Client ID

    @Value("${oauth.naver.secret}")
    private String clientSecretKey; // 앱 키 중 Client Secret

    @Value("${oauth.naver.url.auth}")
    private String naverUrl;

    @Value("${oauth.naver.url.api}")
    private String naverApiUrl;



}
