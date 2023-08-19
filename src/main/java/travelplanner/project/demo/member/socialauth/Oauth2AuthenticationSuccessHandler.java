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
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.auth.AuthResponse;
import travelplanner.project.demo.member.socialauth.kakao.KakaoUserInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${secret.key}")
    private String SECRET_KEY;

    static final long AccessTokenValidTime = 5 * 60 * 1000L;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUrl;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String targetUrl = determineTargetUrl(request, response, authentication);
        log.info(targetUrl+"------------------targetUrl");
        if(response.isCommitted()) {
            log.debug("Response has already been committed.");
            return;
        }
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(principalDetails.getAttributes());

        // Oauth 로그인에 사용할 email 가져오기
        String email = kakaoUserInfo.getEmail();

        // 반환할 userId 세팅
        Optional<Member> kakaoUser = memberRepository.findByEmail(email);
        Long userId = kakaoUser.get().getId();

        // 인증이 성공했을 때, 어세스 토큰과 리프레시 토큰 발급
        String accessToken = generateAccessToken(email);

        // 어세스 토큰은 헤더에 담아서 응답으로 보냄
        response.setHeader("Authorization", accessToken);

        // 리프레시 토큰을 Redis 에 저장
        if (getData(email) == null) {
            String refreshToken = generateRefreshToken(email);
            // 리프레시 토큰은 쿠키에 담아서 응답으로 보냄
            cookieCreate(refreshToken, response);
            setData(email, refreshToken);
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

    public String generateAccessToken(String email){
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + AccessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken(String email) {

        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        // 저장
        setData(email, refreshToken);

        return refreshToken;
    }

    public String getData(String key) {

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setData(String key, String value) {

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public static void cookieCreate(String value, HttpServletResponse response) {

        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", value)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(false)
                .maxAge(Integer.MAX_VALUE) // 리프레시 토큰은 브라우저를 닫더라도 계속 유지되도록 설정
                .build();

        response.addHeader("Set-Cookie", responseCookie.toString());
    }


}
