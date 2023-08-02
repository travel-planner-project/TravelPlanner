package travelplanner.project.demo.global.util;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.exception.ExceptionType;
import travelplanner.project.demo.global.exception.TokenExpiredException;

import java.util.Date;

@Component
@RequiredArgsConstructor
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
            throw new Exception(ExceptionType.TOKEN_IS_NOT_MATCHED);
        }
    }

    public String getEmail (String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public String refreshAccessToken(String refreshToken) throws Exception {

        String email = getEmail(refreshToken);

        // Redis에서 리프레시 토큰을 가져온다.
        String storedRefreshToken = redisUtil.getData(email);

        // 저장된 리프레시 토큰과 제공된 리프레시 토큰이 동일한지 검사
        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new Exception(ExceptionType.TOKEN_IS_NOT_MATCHED);
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
}
