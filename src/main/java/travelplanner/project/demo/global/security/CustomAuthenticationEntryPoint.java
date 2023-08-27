package travelplanner.project.demo.global.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.security.jwt.TokenExpiredException;
import travelplanner.project.demo.global.util.CookieUtil;
import travelplanner.project.demo.global.util.RedisUtil;
import travelplanner.project.demo.global.util.TokenUtil;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final CookieUtil cookieUtil;
    private final TokenUtil tokenUtil;
    private final RedisUtil redisUtil;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, IOException {

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/oauth") || requestURI.startsWith("/login")) {
            // /oauth로 시작하는 URL은 처리 생략
            return;
        }


        if (authException instanceof TokenExpiredException) {
            ErrorType errorType = ErrorType.ACCESS_TOKEN_EXPIRED;
            setResponse(response, errorType);

        } else if (authException instanceof UsernameNotFoundException) { // 로그인시 유저 아이디가 일치하지 않는 경우의 에러 커스텀
            ErrorType errorType = ErrorType.CHECK_EMAIL_AGAIN;
            setResponse(response, errorType);

        } else if (authException instanceof BadCredentialsException) { // 로그인시 유저 비밀번호가 일치하지 않는 경우의 에러 커스텀
            ErrorType errorType = ErrorType.CHECK_PASSWORD_AGAIN;
            setResponse(response, errorType);

        } else {
            ErrorType errorType = ErrorType.TOKEN_USER_DOES_NOT_AUTHORIZED;
            setResponse(response, errorType);
        }
    }

    // 클라이언트에게 직접 메세지를 보내기 위한 커스텀 response
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

