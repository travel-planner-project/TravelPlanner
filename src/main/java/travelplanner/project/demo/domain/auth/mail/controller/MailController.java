package travelplanner.project.demo.domain.auth.mail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.domain.auth.mail.dto.ChangePasswordDto;
import travelplanner.project.demo.domain.auth.mail.dto.MailDto;
import travelplanner.project.demo.domain.auth.mail.service.ForgotPasswordService;
import travelplanner.project.demo.domain.auth.mail.service.MailService;
import travelplanner.project.demo.global.exception.ApiExceptionResponse;

@Tag(name = "Auth", description = "회원가입/로그인 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/password")
public class MailController {

    private final ForgotPasswordService forgotPasswordService;
    private final MailService mailService;

    @Operation(summary = "비밀번호를 변경하기 위한 이메일 인증 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메일 전송 성공"),
            @ApiResponse(responseCode = "404", description = "유저가 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "입력하지  않은 요소가 존재합니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping("/forgot")
    public String getChangePasswordApi(@RequestBody MailDto mailDto) {

        String email = mailDto.getEmail();
        String resetLink = forgotPasswordService.generateTempToken(email);
        mailService.sendSimpleMessage(email, "비밀번호 변경", "비밀번호를 변경하려면 다음 링크를 클릭하세요: " + resetLink);
        return null;
    }

//    @GetMapping("/reset/**")
//    public String getPasswordChangePage (@RequestParam String tempToken) {
//        return "http://localhost:8080/password/change?tempToken=" + tempToken;
//    }

    @Operation(summary = "비밀번호 변경 callback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "변경 주소 반환 성공"),
    })
    @GetMapping("/callback")
    public String getChangePasswordUrl(@RequestParam String tempToken) {
        return "http://localhost:5173/password/change?tempToken=" + tempToken;
    }

    @Operation(summary = "비밀번호 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "유저가 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping("/change")
    public void changePassword(@RequestParam String tempToken, @RequestBody ChangePasswordDto changePasswordDto) {
        forgotPasswordService.changePassword(changePasswordDto, tempToken);
    }

}
