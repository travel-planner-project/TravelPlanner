package travelplanner.project.demo.mail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.mail.dto.ChangePasswordDto;
import travelplanner.project.demo.mail.dto.MailDto;
import travelplanner.project.demo.mail.service.ForgotPasswordService;
import travelplanner.project.demo.mail.service.MailService;
import travelplanner.project.demo.member.MemberRepository;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class MailController {

    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot")
    public String getChangePasswordApi(@RequestBody MailDto mailDto) {

        forgotPasswordService.generateTempToken(mailDto.getEmail());

        return null;
    }

    @PostMapping("/change")
    public String getUriMailToken(@RequestBody ChangePasswordDto changePasswordDto) {

        forgotPasswordService.changePassword(changePasswordDto);

        return null;
    }

}
