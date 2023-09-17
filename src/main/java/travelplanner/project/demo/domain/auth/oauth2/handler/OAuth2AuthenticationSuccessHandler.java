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
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String naverRedirectUri;

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

        String targetUrl = determineTargetUrl(request, response, authentication);
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

        // JSON 형태로 변환하여 응답 보내기
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(authResponse));
        writer.flush();

        // 리다이렉트 수행
        super.onAuthenticationSuccess(request, response, authentication);
    }

    // 소셜 종류에 따른 리다이렉트 결정
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        if (customOAuth2User.getUser().getProvider().equals("kakao")) {
            return kakaoRedirectUri;

        } else if (customOAuth2User.getUser().getProvider().equals("google")) {
            return googleRedirectUri;

        } else if (customOAuth2User.getUser().getProvider().equals("naver")) {
            return naverRedirectUri;
        }

        return null;
    }
}
