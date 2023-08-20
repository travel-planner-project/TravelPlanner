package travelplanner.project.demo.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public static void create(String value, HttpServletResponse response) {

        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", value)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(false)
                .maxAge(Integer.MAX_VALUE) // 리프레시 토큰은 브라우저를 닫더라도 계속 유지되도록 설정
                .build();

        response.addHeader("Set-Cookie", responseCookie.toString());
    }

    public static void delete(String value, HttpServletResponse response) {

        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", value)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(false)
                .maxAge(0)
                .build();

        response.addHeader("Set-Cookie", responseCookie.toString());
    }

    public Cookie getRefreshTokenCookie(HttpServletRequest request) {
        return getCookie(request, "refreshToken");
    }

    public Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }

        return null;
    }
}
