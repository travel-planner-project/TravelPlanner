package travelplanner.project.demo.mail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.AESUtil;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.mail.dto.ChangePasswordDto;
import travelplanner.project.demo.mail.dto.MailDto;
import travelplanner.project.demo.mail.service.ForgotPasswordService;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class MailController {

    private final TokenUtil tokenUtil;
    private final MemberRepository memberRepository;
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot")
    public String getChangePasswordApi(@RequestBody MailDto mailDto) {
        String tempToken = forgotPasswordService.generateTempToken(mailDto.getEmail(), mailDto.getNickName());
        String resetLink = "https://yourwebsite.com/reset-password?token=" + tempToken;

        return null;
    }

    @PostMapping("/change")
    public String getUriMailToken(@RequestBody ChangePasswordDto changePasswordDto) {

        if (!tokenUtil.isValidToken(changePasswordDto.getToken())) {
            throw new ApiException(ErrorType.TOKEN_NOT_VALID);
        }

        String email = tokenUtil.getEmailFromToken(changePasswordDto.getToken());

        // 이메일을 사용하여 멤버 찾기
        Member memberToUpdate = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        return null;
    }

}
