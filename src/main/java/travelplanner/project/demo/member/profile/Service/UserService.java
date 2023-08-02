package travelplanner.project.demo.member.profile.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.exception.ExceptionType;
import travelplanner.project.demo.global.util.RedisUtil;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;
import travelplanner.project.demo.member.profile.dto.request.PasswordCheckRequest;
import travelplanner.project.demo.member.profile.dto.request.PasswordUpdateRequest;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private MemberRepository memberRepository;
    private ProfileRepository profileRepository;
    private S3Service s3Service;
    private TokenUtil tokenUtil;
    private RedisUtil redisUtil;
    private PasswordEncoder encoder;


    // 비밀번호 체크
    public boolean checkUserPassword(PasswordCheckRequest request, HttpServletRequest httpRequest) throws Exception {

        Member currentMember = getCurrentMember();
        String encodedPassword = currentMember.getPassword();

        if (encoder.matches(request.getPassword(), encodedPassword)) {
            return true;

        } else {
            throw new Exception(ExceptionType.CHECK_PASSWORD_AGAIN);
        }
    }

    // 비밀번호 변경
    @Transactional
    public void updatePassword(PasswordUpdateRequest request, HttpServletRequest httpRequest) {

        Member currentMember = getCurrentMember();

        if (currentMember == null) {
            throw new Exception(ExceptionType.USER_NOT_FOUND);
        }

        // 로그인한 유저와 요청한 유저가 동일한지 확인
        boolean isCurrentUser = currentMember.getId().equals(request.getUserId());

        if (!isCurrentUser) {
            throw new Exception(ExceptionType.THIS_USER_IS_NOT_SAME_LOGIN_USER);
        }

        String encodedPassword = encoder.encode(request.getPassword());
        currentMember.setPassword(encodedPassword);

        memberRepository.save(currentMember);
    }

    // 회원탈퇴
    @Transactional
    public void deleteUser(PasswordCheckRequest request, HttpServletRequest httpRequest) {

        Member currentMember = getCurrentMember();

        if (currentMember == null) {
            throw new Exception(ExceptionType.USER_NOT_FOUND);
        }

        // 로그인한 유저와 요청한 유저가 동일한지 확인
        boolean isCurrentUser = currentMember.getId().equals(request.getUserId());

        if (!isCurrentUser) {
            throw new Exception(ExceptionType.THIS_USER_IS_NOT_SAME_LOGIN_USER);
        }

        Profile profile = profileRepository.findProfileByMemberId(currentMember.getId());

        // 프로필 이미지 삭제하기
        s3Service.deleteFile(profile.getKeyName());

        // 프로필 삭제
        profileRepository.delete(profile);

        // 레디스 삭제
        redisUtil.deleteData(currentMember.getEmail());

        // 멤버 삭제
        memberRepository.delete(currentMember);
    }

    private Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 사용자의 email 얻기
        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
    }
}
