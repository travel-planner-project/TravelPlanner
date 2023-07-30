package travelplanner.project.demo.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public Cookie create(String value) {

        Cookie cookie = new Cookie("refreshToken", value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        // 리프레시 토큰은 브라우저를 닫더라도 계속 유지되도록 설정
        cookie.setMaxAge(Integer.MAX_VALUE);

        return cookie;
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
