package travelplanner.project.demo.domain.auth.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travelplanner.project.demo.domain.auth.auth.service.AuthService;
import travelplanner.project.demo.domain.auth.auth.dto.response.AuthResponse;
import travelplanner.project.demo.domain.auth.auth.dto.request.LoginRequest;
import travelplanner.project.demo.domain.auth.auth.dto.request.RegisterRequest;
import travelplanner.project.demo.global.exception.ApiExceptionResponse;


@Tag(name = "User", description = "회원가입 / 로그인 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService service;

    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 이메일 입니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "입력하지 않은 요소가 존재합니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping("/auth/signup")
    public void signup(@RequestBody RegisterRequest request) {
        service.register(request);
    }

    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 정보가 일치하지 않는 경우",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) throws Exception {
        AuthResponse authResponse = service.login(request, response);
        return ResponseEntity.ok().body(authResponse);
    }

    @Operation(summary = "로그아웃")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
    })
    @PostMapping("/auth/logout")
    public void logout(HttpServletResponse response) {
        service.logout(response);
    }
}
