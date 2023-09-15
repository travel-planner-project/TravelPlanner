package travelplanner.project.demo.domain.auth.mail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.domain.auth.mail.dto.ChangePasswordDto;
import travelplanner.project.demo.domain.auth.mail.dto.MailDto;
import travelplanner.project.demo.domain.auth.mail.service.ForgotPasswordService;

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
