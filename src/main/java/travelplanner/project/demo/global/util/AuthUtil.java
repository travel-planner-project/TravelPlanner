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
import travelplanner.project.demo.planner.domain.GroupMember;
import travelplanner.project.demo.planner.repository.GroupMemberRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthUtil {

    private final TokenUtil tokenUtil;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;

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

    /**
     * 그룹 멤버인지 확인하는 로직이 너무 많아서 만들었습니다. 아래의 내용과 비슷한 로직이 있다면 추후에 리팩토링하면 될 것 같습니다.
     * 이 메서드는 주어진 이메일이 특정 플래너의 그룹 멤버인지 여부를 확인합니다.
     * @param email 확인하려는 사용자의 이메일 주소입니다. 비회원의 경우 null 일 수 있습니다.
     * @param plannerId 그룹 멤버인지 확인하려는 플래너의 고유 ID입니다.
     * @return 이메일이 플래너의 그룹 멤버에 포함되어 있으면 true를 반환하고, 그렇지 않으면 false를 반환합니다.
     */
    public boolean isGroupMember(String email, Long plannerId) {
        // email이 null인 경우, 사용자가 비회원이므로 그룹 멤버가 아닌 것으로 판단하고 false를 반환합니다.
        if (email == null) {
            return false;
        }
        // 플래너 ID로 해당 플래너의 그룹 멤버 목록을 가져옵니다.
        List<GroupMember> groupMembers = groupMemberRepository.findGroupMemberByPlannerId(plannerId);
        // 그룹 멤버 목록에서 주어진 이메일과 일치하는 멤버가 있는지 확인합니다.
        // stream()과 anyMatch()를 사용하여 이메일이 일치하는 그룹 멤버가 하나라도 있으면 true를 반환합니다.
        return groupMembers.stream().anyMatch(gm -> gm.getEmail().equals(email));
    }
}
