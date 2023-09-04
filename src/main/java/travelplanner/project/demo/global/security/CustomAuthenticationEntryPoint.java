package travelplanner.project.demo.global.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.global.exception.ErrorType;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        ErrorType errorType = null;
        String requestURI = request.getRequestURI();

        if (authException instanceof UsernameNotFoundException) { // 로그인시 유저 아이디가 일치하지 않는 경우의 에러 커스텀
            setResponse(response, ErrorType.CHECK_EMAIL_AGAIN);

        } else if (authException instanceof BadCredentialsException) { // 로그인시 유저 비밀번호가 일치하지 않는 경우의 에러 커스텀
            setResponse(response, ErrorType.CHECK_PASSWORD_AGAIN);

        } else if (authException instanceof InsufficientAuthenticationException) { // 인가에 실패한 경우
            setResponse(response, ErrorType.ACCESS_TOKEN_EXPIRED);
        }
    }

    private void setResponse(HttpServletResponse response, ErrorType errorType) throws IOException {

        response.setContentType("application/json;charset=UTF-8");

        int status = Integer.parseInt(String.valueOf(errorType.getStatus()).substring(0,3));
        response.setStatus(status);

        response.getWriter().println(
                "{\"status\" : \"" +  errorType.getStatus() + "\"," +
                        "\"errorCode\" : \"" + errorType.getErrorCode() + "\"," +
                        " \"message\" : \"" + errorType.getMessage() +
                        "\"}");
    }
}

