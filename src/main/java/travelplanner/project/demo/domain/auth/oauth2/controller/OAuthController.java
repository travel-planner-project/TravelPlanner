package travelplanner.project.demo.domain.auth.oauth2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "OAuth2.0", description = "소셜 로그인 API")
@RestController
@RequestMapping("/oauth/authorize")
public class OAuthController {

    @Operation(summary = "구글 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소셜 로그인 성공")
    })
    @GetMapping("/google")
    public void googleLogin() {
    }

    @Operation(summary = "카카오 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소셜 로그인 성공")
    })
    @GetMapping("/kakao")
    public void kakaoLogin() {
    }

    @Operation(summary = "네이버 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소셜 로그인 성공")
    })
    @GetMapping("/naver")
    public void naverLogin() {
    }
}
