package travelplanner.project.demo.domain.planner.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import travelplanner.project.demo.domain.planner.chat.dto.request.ChatRequest;
import travelplanner.project.demo.domain.planner.chat.service.ChatService;
import travelplanner.project.demo.global.util.TokenUtil;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final TokenUtil tokenUtil;

    @MessageMapping("/chat/{plannerId}")
    public void sendChat(
            @DestinationVariable Long plannerId,
            ChatRequest request,
            @Header("Authorization") String accessToken) {

        tokenUtil.getAuthenticationFromToken(accessToken);
        messagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","chat", "msg", chatService.sendChat(accessToken, request, plannerId)
                )
        );
    }

}
