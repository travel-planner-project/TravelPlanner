package travelplanner.project.demo.member.Auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.member.MemberRepository;



@Tag(name = "User", description = "회원가입 / 로그인 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService service;
    private final MemberRepository repository;


    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 유저 / 유효성 검증 실패",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @PostMapping("/auth/signup")
    public void signup(@RequestBody RegisterRequest request) {
        service.register(request);
    }



    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "403", description = "로그인 정보가 일치하지 않는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @PostMapping("/auth/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest request) {

        return ResponseEntity.ok(service.login(request));
    }
}
