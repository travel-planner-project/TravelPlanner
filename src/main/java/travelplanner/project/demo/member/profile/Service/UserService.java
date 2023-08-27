package travelplanner.project.demo.member.profile.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.AuthUtil;
import travelplanner.project.demo.global.util.RedisUtil;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberEditor;
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
    private AuthUtil authUtil;
    private RedisUtil redisUtil;
    private PasswordEncoder encoder;


    // 비밀번호 체크
    public boolean checkUserPassword(PasswordCheckRequest request) throws ApiException {

        Member currentMember = authUtil.getCurrentMember();
        String encodedPassword = currentMember.getPassword();

        if (encoder.matches(request.getPassword(), encodedPassword)) {
            return true;

        } else {
            throw new ApiException(ErrorType.CHECK_PASSWORD_AGAIN);
        }
    }

    // 비밀번호 변경
    @Transactional
    public void updatePassword(PasswordUpdateRequest request) {
        Member currentMember = authUtil.getCurrentMember();
        String encodedPassword = encoder.encode(request.getPassword());

        MemberEditor memberEditor = MemberEditor.builder()
                .password(encodedPassword)
                .build();

        currentMember.edit(memberEditor); // MemberEditor를 사용하여 비밀번호 수정
        memberRepository.save(currentMember);
    }


    // 회원탈퇴
    @Transactional
    public void deleteUser() {

        Member currentMember = authUtil.getCurrentMember();

        Profile profile = profileRepository.findProfileByMemberId(currentMember.getId());

        // 프로필 이미지 삭제하기
        s3Service.deleteFile(profile.getKeyName());

        // 레디스 삭제
        redisUtil.deleteData(currentMember.getEmail());

        // 멤버 삭제
        memberRepository.delete(currentMember);
    }
}
