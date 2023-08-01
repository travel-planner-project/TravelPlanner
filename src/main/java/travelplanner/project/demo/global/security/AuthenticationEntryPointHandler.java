package travelplanner.project.demo.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.global.exception.ExceptionType;


import java.io.IOException;

@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");
        ExceptionType errorCode;


        /**
         * 토큰 없는 경우
         */
        if(exception == null) {
            setResponse(response, ExceptionType.TOKEN_NOT_EXISTS);
            return;
        }

        /**
         * 토큰 만료된 경우
         */
        if(exception.equals(HttpStatus.UNAUTHORIZED)) {
            errorCode = ExceptionType.TOKEN_NOT_EXISTS;
            setResponse(response, errorCode);
            return;
        }

        /**
         * 토큰 시그니처가 다른 경우
         */
        if(exception.equals(HttpStatus.BAD_REQUEST)) {
            errorCode = ExceptionType.INVALID_INPUT_VALUE;
            setResponse(response, errorCode);
        }
    }

    /**
     * 한글 출력을 위해 getWriter() 사용
     */
    private void setResponse(HttpServletResponse response, ExceptionType errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("{ \"message\" : \"" + errorCode.getMessage()
                + "\", \"code\" : \"" +  errorCode.getStatus()
                + "\"}");
    }

}