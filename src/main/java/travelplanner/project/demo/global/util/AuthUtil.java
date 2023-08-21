package travelplanner.project.demo.global.util;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthUtil {

    private final MemberRepository memberRepository;
    private final TokenUtil tokenUtil;

    public Member getCurrentMember() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 사용자의 email 얻기
        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
    }

    /*
    이 메서드는 플래너 조회 떄문에 만들었습니다. 토큰 필터에서 /planner/** 와 GET 요청은 걸러지기 때문에, 플래너 조회를 모든 사람이 할 수 있게 되었습니다.
    이 조건을 지우면, 피드 -> 상세조회 갈 때 문제가 되므로, 토큰필터와 인증 필터를 거치는것 까지 허용하고
    플래너 서비스 단계에서 헤더에 있는 어세스토큰을 가져와 유저인증을 시큐리티에게 넘겨주어 유저인증을 마치는것으로 변경하였습니다.
    다른 좋은 방법이 있으면 알려주세요!
     */
    public void authenticationUser(HttpServletRequest request) {

        String accessToken = tokenUtil.getJWTTokenFromHeader(request);
        String tokenUtilEmail = tokenUtil.getEmail(accessToken);

        log.info("유저 정보: " + tokenUtilEmail);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(tokenUtilEmail, accessToken, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
