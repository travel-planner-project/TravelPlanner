package travelplanner.project.demo.global.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.global.util.TokenUtil;

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
        log.info("Incoming message type: " + accessor.getMessageType());

        // websocket 연결 시 헤더의 JWT 토큰 유효성 검증

        if (SimpMessageType.CONNECT.equals(accessor.getMessageType())
        || SimpMessageType.MESSAGE.equals(accessor.getMessageType())) {
            log.info("accessor: " + accessor.getMessageType());

            if (tokenUtil.isValidToken(accessToken)) {
                tokenUtil.getAuthenticationFromToken(accessToken);
            }
        }
        return message;
    }
}

