package server.global.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("=========================================================================");
        log.debug("================================== BEGIN =================================");
        log.debug("Request URI: " + request.getRequestURI());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
