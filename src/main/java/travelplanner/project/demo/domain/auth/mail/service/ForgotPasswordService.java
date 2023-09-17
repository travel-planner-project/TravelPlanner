package travelplanner.project.demo.domain.auth.mail.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.member.editor.MemberEditor;
import travelplanner.project.demo.domain.member.repository.MemberRepository;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.RedisUtil;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.domain.auth.mail.dto.ChangePasswordDto;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class ForgotPasswordService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final MailService mailService;
    private final TokenUtil tokenUtil;
    private final RedisUtil redisUtil;
    private static final String PROVIDER_LOCAL = "local";
    // 임시 토큰 생성
    public String generateTempToken(String email) {

        Member member = memberRepository.findMemberByEmailAndProvider(email, PROVIDER_LOCAL)
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        String tempToken = tokenUtil.generateTempToken(member.getId());  // TokenUtil에 임시 토큰 생성 메서드 추가
        redisUtil.setDataExpireWithPrefix("temp", email, tempToken, Duration.ofMinutes(30));
        String resetLink = "http://localhost:5173/password/callback?tempToken=" + tempToken;
        return resetLink;
    }

    public void changePassword(ChangePasswordDto changePasswordDto, String tempToken) {


        // URI 부분을 제거
//        String token = changePasswordDto.getToken().replaceFirst("https://localhost:8080/password/reset/reset-password\\?token=", "");

        if (!tokenUtil.isValidToken(tempToken)) {
            throw new ApiException(ErrorType.TOKEN_NOT_VALID);
        }

        String email = tokenUtil.getEmailFromToken(tempToken);

        // 이메일을 사용하여 멤버 찾기
        Member memberToUpdate = memberRepository.findByEmailAndProvider(email, PROVIDER_LOCAL)
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        String newPassword = changePasswordDto.getNewPassword();
        log.info("---------------------------- newPassword: " + newPassword);
        String encodedPassword = encoder.encode(newPassword);

        MemberEditor memberEditor = MemberEditor.builder()
                .password(encodedPassword)
                .build();

        memberToUpdate.edit(memberEditor);
        memberRepository.save(memberToUpdate);
    }
}
