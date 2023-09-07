package travelplanner.project.demo.mail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.AESUtil;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.mail.dto.MailDto;
import travelplanner.project.demo.mail.service.ForgotPasswordService;

@RestController
@RequestMapping("/forgot-password")
@RequiredArgsConstructor
public class MailController {

    private final TokenUtil tokenUtil;
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping
    public String getChangePasswordApi(@RequestBody MailDto mailDto) {
        String tempToken = forgotPasswordService.generateTempToken(mailDto.getEmail(), mailDto.getNickName());
        String resetLink = "https://yourwebsite.com/reset-password?token=" + tempToken;

        return null;
    }

    @GetMapping
    public String getUriMailToken(@RequestParam String token) {

        if (!tokenUtil.isValidToken(token)) {
            throw new ApiException(ErrorType.TOKEN_NOT_VALID);
        }

        String email = tokenUtil.getEmailFromToken(token);


        return null;
    }

}
