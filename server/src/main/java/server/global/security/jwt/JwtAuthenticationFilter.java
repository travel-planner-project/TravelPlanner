package server.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import server.global.util.GlobalRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Access / Refresh 헤더에서 토큰을 가져옴.
        String accessToken = jwtUtil.getHeaderToken(request, "Access");
        String refreshToken = jwtUtil.getHeaderToken(request, "Refresh");

        if(accessToken != null) {

            if(jwtUtil.tokenValidation(accessToken)){
                setAuthentication(jwtUtil.getEmailFromToken(accessToken));
            }
            else if (refreshToken != null) {

                // 리프레시 토큰 검증 && 리프레시 토큰 DB에서  토큰 존재유무 확인
                boolean isRefreshToken = jwtUtil.refreshTokenValidation(refreshToken);

                // 리프레시 토큰이 유효하고 리프레시 토큰이 DB와 비교했을때 똑같다면
                if (isRefreshToken) {

                    // 리프레시 토큰으로 아이디 정보 가져오기
                    String loginId = jwtUtil.getEmailFromToken(refreshToken);

                    // 새로운 어세스 토큰 발급
                    String newAccessToken = jwtUtil.createToken(loginId, "Access");

                    // 헤더에 어세스 토큰 추가
                    jwtUtil.setHeaderAccessToken(response, newAccessToken);

                    // Security context에 인증 정보 넣기
                    setAuthentication(jwtUtil.getEmailFromToken(newAccessToken));
                }

                // 리프레시 토큰이 만료 || 리프레시 토큰이 DB와 비교했을때 똑같지 않을 때
                else {
                    jwtExceptionHandler(response, "RefreshToken Expired", HttpStatus.BAD_REQUEST);
                    return;
                }
            }
        }

        filterChain.doFilter(request,response);
    }

    // SecurityContext 에 Authentication 객체 저장
    public void setAuthentication(String email) {
        Authentication authentication = jwtUtil.createAuthentication(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Jwt 예외 처리
    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new GlobalRequest(msg, status.value()));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}