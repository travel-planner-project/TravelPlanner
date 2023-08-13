package travelplanner.project.demo.global.util;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.exception.TokenExpiredException;

import java.util.ArrayList;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenUtil {

    private final RedisUtil redisUtil;
    private static final String SECRET_KEY = "68a4ef27a3f2f0f605a6781e6be34b466b5da3d11db5384218c407e99e6dcecf3361e1f6def13c78f2deb1e6e822bef2ca1c95b1166c97c5278ad81fdba4538";

    // Access 토큰 유효시간 15 분
    static final long AccessTokenValidTime = 3 * 60 * 1000L;

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

    public String generateRefreshToken(String email) {

        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        // 저장
        redisUtil.setData(email, refreshToken);

        return refreshToken;
    }

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            return claims.getBody()
                    .getExpiration()
                    .after(new Date());

        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();
        } catch (Exception e) {
            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
        }
    }

    public String getEmail (String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public String refreshAccessToken(String refreshToken) throws ApiException {

        String email = getEmail(refreshToken);

        // Redis에서 리프레시 토큰을 가져온다.
        String storedRefreshToken = redisUtil.getData(email);

        // 저장된 리프레시 토큰과 제공된 리프레시 토큰이 동일한지 검사
        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
        }

        // 리프레시 토큰의 사용자 정보를 기반으로 새로운 어세스 토큰 발급
        return generateAccessToken(email);
    }

    // JWT 토큰을 헤더에서 추출하는 메서드
    public String getJWTTokenFromHeader(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader;
        }
        return null;
    }

    // 웹소켓에서 받은 토큰을 전달
    public void getJWTTokenFromWebSocket(String authorization) {

        String principal = getEmail(authorization);
        log.info("어세스토큰: " + authorization);
        log.info("유저 이메일: " + principal);

        // JWT 토큰이 유효하면, 사용자 정보를 연결 세션에 추가
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(principal, authorization, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 사용자의 email 얻기
        log.info("authentication: " + authentication);
        log.info("username: " + username);
    }
}
