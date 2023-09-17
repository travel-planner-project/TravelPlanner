package travelplanner.project.demo.domain.auth.mail.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.domain.auth.mail.dto.ChangePasswordDto;
import travelplanner.project.demo.domain.auth.mail.dto.MailDto;
import travelplanner.project.demo.domain.auth.mail.service.ForgotPasswordService;
import travelplanner.project.demo.domain.auth.mail.service.MailService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/password")
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

    @GetMapping("/reset/**")
    public String getPasswordChangePage (@RequestParam String tempToken) {
        return "http://localhost:8080/password/change?tempToken=" + tempToken;
    }

    @PostMapping("/change/**")
    public String getUriMailToken(@RequestParam String tempToken, @RequestBody ChangePasswordDto changePasswordDto) {
        forgotPasswordService.changePassword(changePasswordDto, tempToken);
        return "성공";
    }

}
