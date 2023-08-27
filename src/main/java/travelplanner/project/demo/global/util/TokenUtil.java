package travelplanner.project.demo.global.util;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.security.jwt.TokenExpiredException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenUtil {

    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;

    @Value("${secret.key}")
    private String SECRET_KEY;

    // Access 토큰 유효시간 15 분
    static final long AccessTokenValidTime = 2 * 60 * 1000L;

    public String generateAccessToken(String email) {

        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + AccessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    // 리프레시토큰 발행
    public String generateRefreshToken(String email) {

        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        // 저장
        redisUtil.setDataExpire(email, refreshToken, Duration.ofMinutes(3)); // 테스트를 위해 10 분간 저장

        return refreshToken;
    }


    // 토큰의 유효성 검사
    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            return claims.getBody()
                    .getExpiration()
                    .after(new Date());

        } catch (TokenExpiredException e) { // 어세스 토큰 만료
            e.printStackTrace();
        }
        return false;
    }


    // 어세스 토큰에서 이메일 얻기
    public String getEmail (String token) {
        String email = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject();
        log.info("--------------TokenUtil.getEmail(token)" + email);
        return email;
    }


    // 어세스 토큰 재발행
    public String refreshAccessToken(String refreshToken) throws ApiException {

        String email = getEmail(refreshToken);

        // 리프레시 토큰의 사용자 정보를 기반으로 새로운 어세스 토큰 발급
        return generateAccessToken(email);
    }


    // 어세스 토큰을 헤더에서 추출하는 메서드
    public String getJWTTokenFromHeader(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        log.info("-------------------------Authorization Header: " + authorizationHeader); // 로그 추가
        if (authorizationHeader != null) {
            return authorizationHeader;
        }
        return null;
    }


    // 웹소켓에서 받은 토큰을 전달
    public void getJWTTokenFromWebSocket(String authorization) {

        String principal = getEmail(authorization);
        log.info("-------------------------어세스토큰: " + authorization);
        log.info("-------------------------유저 이메일: " + principal);

        // JWT 토큰이 유효하면, 사용자 정보를 연결 세션에 추가
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(principal, authorization, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 사용자의 email 얻기
        log.info("-------------------------authentication: " + authentication);
        log.info("-------------------------username: " + username);
    }
}
