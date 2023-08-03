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
        if (authException instanceof TokenExpiredException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expired");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }

        System.out.println(authException.getClass().getName());

    }
}

