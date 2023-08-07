package travelplanner.project.demo.planner.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.planner.chat.dto.ChatRequest;
import travelplanner.project.demo.planner.chat.service.ChatService;

@RestController
@RequiredArgsConstructor
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final TokenUtil tokenUtil;

    @MessageMapping("/pub/chat/{plannerId}")
    public void sendChat(
            @DestinationVariable Long plannerId,
            ChatRequest request,
            @Header("Authorization") String athorization) throws Exception{

        tokenUtil.getJWTTokenFromWebSocket(athorization);
        messagingTemplate.convertAndSend("/sub/planner-message/" + plannerId, chatService.sendChat(request, plannerId));
    }

}
