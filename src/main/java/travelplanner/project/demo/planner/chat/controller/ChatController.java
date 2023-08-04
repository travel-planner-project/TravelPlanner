package travelplanner.project.demo.planner.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import travelplanner.project.demo.planner.chat.dto.ChatRequest;
import travelplanner.project.demo.planner.chat.service.ChatService;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("pub/chat/{plannerId}")
    public void sendChat(
            @DestinationVariable Long plannerId,
            ChatRequest request
    ) throws Exception{
        messagingTemplate.convertAndSend(
                "/sub/chat/" + plannerId
                , chatService.sendChat(request, plannerId)
        );

    }

}
