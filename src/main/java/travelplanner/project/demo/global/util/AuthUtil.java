package travelplanner.project.demo.global.util;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.member.repository.MemberRepository;
import travelplanner.project.demo.domain.planner.groupmember.domain.GroupMember;
import travelplanner.project.demo.domain.planner.groupmember.repository.GroupMemberRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthUtil {

    private final TokenUtil tokenUtil;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;

    public Member getCurrentMember(HttpServletRequest request) {
        String accessToken = tokenUtil.getJWTTokenFromHeader(request);
        String userId = tokenUtil.getUserIdFromToken(accessToken);

        return memberRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));
    }

    public Member getCurrentMemberForWebSocket(String accessToken) {
        String userId = tokenUtil.getUserIdFromToken(accessToken);

        return memberRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));
    }


    /**
     * 이 메서드는 플래너 조회 때문에 만들었습니다.
     * 토큰 필터에서 /planner/** 와 GET 요청은 걸러지기 때문에 플래너 조회를 모든 사람이 할 수 있게 되었습니다.
     * 이 조건을 지우면, 피드 -> 상세조회 갈 때 문제가 되므로, 토큰 필터와 인증 필터를 거치는 것 까지 허용하고
     * 플래너 서비스 단계에서 헤더에 있는 어세스토큰을 가져와 유저인증을 시큐리티에게 넘겨주어 유저인증을 마치는 것으로 변경하였습니다.
     * 다른 좋은 방법이 있으면 알려주세요!

     * [추가]
     * 플래너 조회에서 파라미터가 있을 시에 토큰 필터와 인증 필터를 거치는 것 까지 허용완료 했습니다.
     * 그 이후가 문제인데, 로그인을 해서 인증을 받을 수 있는 경우와 없는 경우를 나누는게 맞다고 생각합니다.
     * 그래서 준형님의 말대로 Boolean 을 리턴하여 시큐리티 컨텍스트 홀더에 유저를 저장할 수 있는지 없는 지를 판단했습니다.
     */
    public Boolean authenticationUser(HttpServletRequest request) {
        String accessToken = tokenUtil.getJWTTokenFromHeader(request);

        if (accessToken != null) {
            tokenUtil.getAuthenticationFromToken(accessToken);
            return true;
        }

        return false;
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
            log.info("-------------- email이 전달되지 않거나 비회원");
            return false;
        }
        // 플래너 ID로 해당 플래너의 그룹 멤버 목록을 가져옵니다.
        List<GroupMember> groupMembers = groupMemberRepository.findGroupMemberByPlannerId(plannerId);
        // 그룹 멤버 목록에서 주어진 이메일과 일치하는 멤버가 있는지 확인합니다.
        // stream()과 anyMatch()를 사용하여 이메일이 일치하는 그룹 멤버가 하나라도 있으면 true를 반환합니다.
        log.info("-------------- Checking group members for email: " + email);
        log.info("-------------- Group members found: " + groupMembers.toString());
        return groupMembers.stream().anyMatch(gm -> gm.getEmail().equals(email));
    }
}
