package server.domain.user.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    // 인증 / 인가 작업을 해줌
    // 토큰을 생성하고 검증하는 컴포넌트 TokenProvider을 이용
    // 사용자가 로그인 정보로 인증 요청을 보내면 filter가 요청을 받아 인증용 객체 생성
    // 인증용 객체를 AuthenticationManager 에게 보냄
    // 인증이 완료된 객체를 SecurityContext에 저장

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        // 헤더에서 JWT 받아옴
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        // 토큰 유효성 확인
        if(token != null && jwtTokenProvider.validateToken(token)){
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옴
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext에 Authentication 객체를 저장함
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
