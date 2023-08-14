package travelplanner.project.demo.member.socialauth.google;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.auth.AuthResponse;
import travelplanner.project.demo.member.auth.LoginRequest;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class GoogleOauthController {

    private final GoogleOAuth googleOAuth;
    private final GoogleOAuthService googleOAuthService;

    // 구글 로그인 창 접근
    @RequestMapping(value = "oauth/google", method = RequestMethod.GET)
    public String googleLoginUrl() {
        return googleOAuth.getGoogleRedirectUrl();
    }

    // 로그인
    @RequestMapping(value = "oauth/google/callback", method = RequestMethod.GET)
    public AuthResponse callback(@RequestParam(name = "code") String code, HttpServletResponse response) throws IOException {

        return googleOAuthService.googleLogin(code, response);
    }
}
