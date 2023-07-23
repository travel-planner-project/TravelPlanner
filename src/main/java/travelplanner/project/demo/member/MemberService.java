package travelplanner.project.demo.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.exception.ExceptionType;

import java.util.Optional;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 특정 멤버의 존재여부 확인
    public Boolean findMember(Long userId) throws Exception {

        // 특정 유저가 존재하지 않는 경우 exception 처리
        Optional<Member> member = memberRepository.findByUserId(userId);

        if (member.isEmpty()) {
            throw new Exception(ExceptionType.USER_PROFILE_NOT_FOUND);
        }

        return true;
    }

    // 로그인한 유저와 파라미터로 받은 멤버가 일치하는지 확인
    public Boolean checkMember(Long userId, Long loginUserId) throws Exception {

        if (!userId.equals(loginUserId)) {
            throw new Exception(ExceptionType.THIS_USER_IS_NOT_SAME_LOGIN_USER);
        }

        return true;
    }

    // 로그인한 유저 찾기 (return = email)
    public String findLoginUser () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }
}
