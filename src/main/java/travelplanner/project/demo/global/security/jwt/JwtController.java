package travelplanner.project.demo.global.security.jwt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.global.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;
import travelplanner.project.demo.member.Auth.AuthResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JwtController {

    private final TokenUtil tokenUtil;
    private final CookieUtil cookieUtil;

    @Operation(summary = "accessToken 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "accessToken 재발급 성공"),
            @ApiResponse(responseCode = "404", description = "재발급에 문제가 생긴 경우")
    })
    @GetMapping("/token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {

        Cookie refreshTokenCookie = cookieUtil.getCookie(request, "refreshToken");
        if (refreshTokenCookie == null) {
            return ResponseEntity.badRequest().body("Refresh token not found.");
        }

        String refreshToken = refreshTokenCookie.getValue();

        // 어세스 토큰 재발급
        String newAccessToken;
        try {
            newAccessToken = tokenUtil.refreshAccessToken(refreshToken);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate new access token.");
        }

        // 헤더에 추가
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", newAccessToken);

        return ResponseEntity.ok().headers(responseHeaders).build();
    }
}
