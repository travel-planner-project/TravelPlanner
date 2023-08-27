package travelplanner.project.demo.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    // 리프레시 토큰 생성
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


    // 쿠키 삭제
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


    // 쿠키에서 리프레시 토큰 얻기
    public Cookie getRefreshTokenCookie(HttpServletRequest request) {
        return getCookie(request, "refreshToken");
    }


    // 특정 이름의 쿠기 얻기
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
