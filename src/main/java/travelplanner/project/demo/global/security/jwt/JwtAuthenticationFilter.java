package travelplanner.project.demo.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import travelplanner.project.demo.global.exception.TokenExpiredException;
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

        if (requestURI.startsWith("/auth") || requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3") || requestURI.startsWith("/ws")) {

            filterChain.doFilter(request, response);
            return;
        }

        // 헤더에서 JWT 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = null;

        if (authorizationHeader != null) {
            accessToken = authorizationHeader;
        }

        // JWT 토큰 유효성 검사
        try {
            if (!tokenUtil.isValidToken(accessToken)) {
                // 어세스 토큰이 유효하지 않을 경우(만료된 경우 포함), 예외를 던짐
                throw new AuthenticationException("Invalid or expired access token.") {};
            }
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            // 토큰이 만료된 경우, 예외를 던짐
            throw new TokenExpiredException();

        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationException("Error verifying token.") {};
        }


        String principal = tokenUtil.getEmail(accessToken);
        log.info("유저 정보: " + principal);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(principal, accessToken, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
