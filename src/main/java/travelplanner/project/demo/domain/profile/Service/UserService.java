package travelplanner.project.demo.domain.profile.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.member.editor.MemberEditor;
import travelplanner.project.demo.domain.member.repository.MemberRepository;
import travelplanner.project.demo.domain.profile.domain.Profile;
import travelplanner.project.demo.domain.profile.repository.ProfileRepository;
import travelplanner.project.demo.domain.profile.dto.request.PasswordCheckRequest;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.AuthUtil;
import travelplanner.project.demo.global.util.RedisUtil;
import travelplanner.project.demo.domain.profile.dto.request.PasswordUpdateRequest;
import travelplanner.project.demo.global.util.S3Util;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private MemberRepository memberRepository;
    private ProfileRepository profileRepository;
    private S3Util s3Util;
    private AuthUtil authUtil;
    private RedisUtil redisUtil;
    private PasswordEncoder encoder;


    // 비밀번호 체크
    public boolean checkUserPassword(HttpServletRequest request, PasswordCheckRequest passwordCheckRequest) throws ApiException {

        Member currentMember = authUtil.getCurrentMember(request);
        String encodedPassword = currentMember.getPassword();

        if (encoder.matches(passwordCheckRequest.getPassword(), encodedPassword)) {
            return true;

        } else {
            throw new ApiException(ErrorType.CHECK_PASSWORD_AGAIN);
        }
    }

    // 비밀번호 변경
    @Transactional
    public void updatePassword(HttpServletRequest request, PasswordUpdateRequest passwordUpdateRequest) {
        Member currentMember = authUtil.getCurrentMember(request);
        String encodedPassword = encoder.encode(passwordUpdateRequest.getPassword());

        MemberEditor memberEditor = MemberEditor.builder()
                .password(encodedPassword)
                .build();

        currentMember.edit(memberEditor); // MemberEditor를 사용하여 비밀번호 수정
        memberRepository.save(currentMember);
    }


    // 회원탈퇴
    @Transactional
    public void deleteUser(HttpServletRequest request) {

        Member currentMember = authUtil.getCurrentMember(request);

        Profile profile = profileRepository.findProfileByMemberId(currentMember.getId());

        // 프로필 이미지 삭제하기
        s3Util.deleteFile(profile.getKeyName(), "upload/profile/");

        // 레디스 삭제
        redisUtil.deleteData(currentMember.getEmail());

        // 멤버 삭제
        memberRepository.delete(currentMember);
    }
}
