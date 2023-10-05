package travelplanner.project.demo.domain.auth.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.domain.auth.oauth2.principal.CustomOAuth2User;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.member.repository.MemberRepository;
import travelplanner.project.demo.domain.auth.auth.dto.response.AuthResponse;
import travelplanner.project.demo.domain.auth.oauth2.userInfo.GoogleUserInfo;
import travelplanner.project.demo.domain.auth.oauth2.userInfo.KakaoUserInfo;
import travelplanner.project.demo.domain.auth.oauth2.userInfo.NaverUserInfo;
import travelplanner.project.demo.global.util.CookieUtil;
import travelplanner.project.demo.global.util.RedisUtil;
import travelplanner.project.demo.global.util.TokenUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String frontendRedirectUri = "http://localhost:5173";

    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final TokenUtil tokenUtil;
    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String provider = customOAuth2User.getUser().getProvider();
        String email = null;

        if (provider.equals("kakao")) {
            KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(customOAuth2User.getAttributes());
            email = kakaoUserInfo.getEmail();
        } else if (provider.equals("google")) {
            GoogleUserInfo googleUserInfo = new GoogleUserInfo(customOAuth2User.getAttributes());
            email = googleUserInfo.getEmail();
        } else if (provider.equals("naver")) {
            NaverUserInfo naverUserInfo = new NaverUserInfo(customOAuth2User.getAttributes());
            email = naverUserInfo.getEmail();
        }

        String targetUrl = frontendRedirectUri;

        log.info(targetUrl+"------------------targetUrl");
        if(response.isCommitted()) {
            log.debug("------------------ Response 전송 완료");
        }

        log.info("------------------------- 소셜 로그인 성공: " + email + "소셜 타입: " + provider);

        Optional<Member> member = memberRepository.findMemberByEmailAndProvider(email, provider);
        String userId = String.valueOf(member.get().getId());

        // 인증이 성공했을 때, 어세스 토큰과 리프레시 토큰 발급
        String accessToken = tokenUtil.generateAccessToken(userId);
        // 어세스 토큰은 헤더에 담아서 응답으로 보냄
        response.setHeader("Authorization", accessToken);

        // 리프레시 토큰을 Redis 에 저장
        if (redisUtil.getData(userId) == null) {
            String refreshToken = tokenUtil.generateRefreshToken(userId);
            // 리프레시 토큰은 쿠키에 담아서 응답으로 보냄
            cookieUtil.create(refreshToken, response);
        }
        
        AuthResponse authResponse = AuthResponse.builder()
                .userId(member.get().getId())
                .email(member.get().getEmail())
                .userNickname(member.get().getUserNickname())
                .provider(provider)
                .profileImgUrl(member.get().getProfile().getProfileImgUrl())
                .build();

        String encodedEmail = URLEncoder.encode(authResponse.getEmail(), "UTF-8");
        String encodedNickname = URLEncoder.encode(authResponse.getUserNickname(), "UTF-8");
        String encodedProvider = URLEncoder.encode(authResponse.getProvider(), "UTF-8");
        String encodedProfileImgUrl = URLEncoder.encode(authResponse.getProfileImgUrl(), "UTF-8");

        // 프론트엔드 페이지로 토큰과 함께 리다이렉트
        String frontendRedirectUrl = String.format(
                "%s/oauth/callback?token=%s&email=%s&nickname=%s&provider=%s&profileImgUrl=%s",
                frontendRedirectUri, accessToken, encodedEmail, encodedNickname,
                encodedProvider, encodedProfileImgUrl
        );
        response.sendRedirect(frontendRedirectUrl);
    }

}
