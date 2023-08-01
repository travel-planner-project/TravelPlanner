package travelplanner.project.demo.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.global.util.CookieUtil;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/auth") || requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 헤더에서 JWT 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = null;

        if (authorizationHeader != null) {
            accessToken = authorizationHeader;
        }

        // 쿠키에서 리프레시 토큰 추출
        Cookie refreshTokenCookie = cookieUtil.getCookie(request, "refreshToken");
        if (refreshTokenCookie == null) {
            // 리프레시 토큰이 없을 경우, 403 Forbidden 오류 반환
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Refresh token not found.");
            return;
        }

        String refreshToken = refreshTokenCookie.getValue();

        // JWT 토큰 유효성 검사
        if (!tokenUtil.isValidToken(accessToken)) {
            try {

                // 어세스 토큰이 유효하지 않을 경우, 리프레시 토큰으로 새로운 어세스 토큰 발급
                accessToken = tokenUtil.refreshAccessToken(refreshToken);

                log.info("새로운 토큰 생성");

                // 헤더에 어세스 토큰 추가
                response.setHeader("Authorization", accessToken);

            } catch (Exception e) {
                // 리프레시 토큰으로 새로운 어세스 토큰을 발급할 수 없을 경우, 403 Forbidden 오류 반환
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Failed to refresh access token.");
                return;
            }
        }

        String principal = tokenUtil.getEmail(accessToken);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(principal, accessToken, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
