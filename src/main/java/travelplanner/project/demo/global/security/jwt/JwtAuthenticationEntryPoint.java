package travelplanner.project.demo.global.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.global.exception.TokenExpiredException;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/oauth") || requestURI.startsWith("/login")) {
            // /oauth로 시작하는 URL은 처리 생략
            return;
        }

        if (authException instanceof TokenExpiredException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expired");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
}

