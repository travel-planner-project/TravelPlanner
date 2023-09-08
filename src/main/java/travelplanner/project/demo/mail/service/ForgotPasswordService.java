package travelplanner.project.demo.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.RedisUtil;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.mail.dto.ChangePasswordDto;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberEditor;
import travelplanner.project.demo.member.MemberRepository;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final MailService mailService;
    private final TokenUtil tokenUtil;
    private final RedisUtil redisUtil;

    // 임시 토큰 생성
    public void generateTempToken(String email, String nickName) {
        Member member = memberRepository.findByEmailAndUserNickname(email, nickName)
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        String tempToken = tokenUtil.generateTempToken(member.getId());  // TokenUtil에 임시 토큰 생성 메서드 추가
        redisUtil.setDataExpireWithPrefix("temp", email, tempToken, Duration.ofMinutes(30));
        String resetLink = "https://localhost:8080/reset-password?token=" + tempToken;
        mailService.sendSimpleMessage(email, "비밀번호 변경", "비밀번호를 변경하려면 다음 링크를 클릭하세요: " + resetLink);
    }

    public void changePassword(ChangePasswordDto changePasswordDto) {

        // 제외해야함
        // "https://localhost:8080/reset-password?token="

        if (!tokenUtil.isValidToken(changePasswordDto.getToken())) {
            throw new ApiException(ErrorType.TOKEN_NOT_VALID);
        }

        String email = tokenUtil.getEmailFromToken(changePasswordDto.getToken());

        // 이메일을 사용하여 멤버 찾기
        Member memberToUpdate = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        String newPassWord = changePasswordDto.getNewPassWord();
        String encodedPassWord = encoder.encode(newPassWord);

        MemberEditor memberEditor = MemberEditor.builder()
                .password(encodedPassWord)
                .build();

        memberToUpdate.edit(memberEditor);
        memberRepository.save(memberToUpdate);
    }
}
