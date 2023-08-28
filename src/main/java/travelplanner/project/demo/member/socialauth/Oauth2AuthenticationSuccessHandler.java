package travelplanner.project.demo.member.socialauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import travelplanner.project.demo.global.util.CookieUtil;
import travelplanner.project.demo.global.util.RedisUtil;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.auth.AuthResponse;
import travelplanner.project.demo.member.socialauth.kakao.KakaoUserInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUrl;
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final TokenUtil tokenUtil;
    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                          Authentication authentication) throws IOException, ServletException {

        String targetUrl = determineTargetUrl(request, response, authentication);
        log.info(targetUrl+"------------------targetUrl");
        if(response.isCommitted()) {
            log.debug("------------------ Response 전송 완료");
        }

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(principalDetails.getAttributes());

        // Oauth 로그인에 사용할 email 가져오기
        String email = kakaoUserInfo.getEmail();

        // 반환할 userId 세팅
        Optional<Member> kakaoUser = memberRepository.findByEmail(email);
        Long userId = kakaoUser.get().getId();

        // 인증이 성공했을 때, 어세스 토큰과 리프레시 토큰 발급
        String accessToken = tokenUtil.generateAccessToken(email);

        // 어세스 토큰은 헤더에 담아서 응답으로 보냄
        response.setHeader("Authorization", accessToken);

        // 리프레시 토큰을 Redis 에 저장
        if (redisUtil.getData(email) == null) {
            String refreshToken = tokenUtil.generateRefreshToken(email);
            // 리프레시 토큰은 쿠키에 담아서 응답으로 보냄
            cookieUtil.create(refreshToken, response);
            redisUtil.setDataExpire(email, refreshToken, Duration.ofMinutes(3)); // 현재 테스트 기간이라 3분으로 지정
        }


        AuthResponse authResponse = AuthResponse.builder()
                .userId(userId)
                .email(kakaoUserInfo.getEmail())
                .userNickname(kakaoUserInfo.getName())
                .profileImgUrl(kakaoUserInfo.getProfile())
                .build();


        // JSON 형태로 변환하여 응답 보내기
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(authResponse));
        writer.flush();


        // 리다이렉트 수행
        super.onAuthenticationSuccess(request, response, authentication);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        return redirectUrl;
    }
}
