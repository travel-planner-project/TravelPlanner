package travelplanner.project.demo.member.profile.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.exception.ExceptionType;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.MemberService;
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
    private MemberService memberService;
    private PasswordEncoder passwordEncoder;
    private S3Service s3Service;

    // 비밀번호 체크
    public boolean checkUserPassword(PasswordCheckRequest request) throws Exception {

        // 해당 멤버 존재하는지 확인
        memberService.findMember(request.getUserId());

        // 로그인한 사람과 프로필 유저가 사람이 같은지 확인
        Long loginUserId = memberRepository.findByEmail(memberService.findLoginUser()).get().getUserId();
        memberService.checkMember(request.getUserId(), loginUserId);

        Member member = memberRepository.findByUserId(request.getUserId()).get();
        String encodedPassword = member.getPassword();

        if (encodedPassword.equals(member.getPassword())) {
            return true;

        } else {
            throw new Exception(ExceptionType.CHECK_PASSWORD_AGAIN);
        }
    }

    // 비밀번호 변경
    @Transactional
    public void updatePassword(PasswordUpdateRequest request) {

        // 해당 멤버 존재하는지 확인
        memberService.findMember(request.getUserId());

        // 로그인한 사람과 프로필 유저가 사람이 같은지 확인
        Long loginUserId = memberRepository.findByEmail(memberService.findLoginUser()).get().getUserId();
        memberService.checkMember(request.getUserId(), loginUserId);

        Member member = memberRepository.findByUserId(request.getUserId()).get();
        String encodedPassword = passwordEncoder.encode(request.getPassword()); // 비밀번호를 암호화
        member.setPassword(encodedPassword);

        memberRepository.save(member);
    }

    // 회원탈퇴
    @Transactional
    public void deleteUser(PasswordCheckRequest request) {

        Member member = memberRepository.findByUserId(request.getUserId()).get();
        Profile profile = profileRepository.findProfileByMemberUserId(member.getUserId());

        // 프로필 이미지 삭제하기
        s3Service.deleteFile(profile.getKeyName());

        // 프로필 삭제
        profileRepository.delete(profile);

        // 멤버 삭제
        memberRepository.delete(member);
    }
}
