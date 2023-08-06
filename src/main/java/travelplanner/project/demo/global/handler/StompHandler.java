package travelplanner.project.demo.global.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.global.util.TokenUtil;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {

    private final TokenUtil tokenUtil;

    // websocket을 통해 들어온 요청이 처리되기 전 실행됨
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String accessToken = accessor.getFirstNativeHeader("Authorization");
        log.info("Received STOMP Message: " + message);
        log.info("All headers: " + accessor.toNativeHeaderMap());
        log.info("Access Token: " + accessToken);


        // websocket 연결 시 헤더의 JWT 토큰 유효성 검증
        if (StompCommand.SEND == accessor.getCommand()) {

            try {
                if (tokenUtil.isValidToken(accessToken)) {
                    String principal = tokenUtil.getEmail(accessToken);
                    log.info("어세스토큰: " + accessToken);
                    log.info("유저 이메일: " + principal);

                    // JWT 토큰이 유효하면, 사용자 정보를 연결 세션에 추가
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(principal, accessToken, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return message;
    }
}
