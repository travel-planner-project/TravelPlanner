package travelplanner.project.demo.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.exception.ExceptionType;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.global.util.CookieUtil;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, Exception {

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie refreshTokenCookie = cookieUtil.getCookie(request, "refreshToken");
        Cookie accessTokenCookie = cookieUtil.getCookie(request, "accessToken");

        if (refreshTokenCookie == null || accessTokenCookie == null) {

            throw new Exception(ExceptionType.TOKEN_NOT_EXISTS);
        }

        String refreshToken = refreshTokenCookie.getValue();
        String accessToken = accessTokenCookie.getValue();

        if (!tokenUtil.isValidToken(accessToken)) {
            try {
                accessToken = tokenUtil.refreshAccessToken(refreshToken);
                accessTokenCookie = cookieUtil.create("accessToken", accessToken);
                response.addCookie(accessTokenCookie);

            } catch (AuthenticationException e) {
                throw new Exception(ExceptionType.TOKEN_NOT_EXISTS);

            } catch (Exception e) {
                throw new Exception(ExceptionType.INTERNAL_SERVER_ERROR);
            }
        }

        filterChain.doFilter(request, response);
    }
}
