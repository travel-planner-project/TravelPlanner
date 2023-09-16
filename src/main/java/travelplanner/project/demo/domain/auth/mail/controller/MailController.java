package travelplanner.project.demo.domain.auth.mail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.domain.auth.mail.dto.ChangePasswordDto;
import travelplanner.project.demo.domain.auth.mail.dto.MailDto;
import travelplanner.project.demo.domain.auth.mail.service.ForgotPasswordService;
import travelplanner.project.demo.domain.auth.mail.service.MailService;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class MailController {

    private final ForgotPasswordService forgotPasswordService;
    private final MailService mailService;

    @PostMapping("/forgot")
    public String getChangePasswordApi(@RequestBody MailDto mailDto) {

        String email = mailDto.getEmail();
        String resetLink = forgotPasswordService.generateTempToken(email);
        mailService.sendSimpleMessage(email, "비밀번호 변경", "비밀번호를 변경하려면 다음 링크를 클릭하세요: " + resetLink);
        return null;
    }

    @PostMapping("/change")
    public String getUriMailToken(@RequestBody ChangePasswordDto changePasswordDto) {

        forgotPasswordService.changePassword(changePasswordDto);

        return null;
    }

}
