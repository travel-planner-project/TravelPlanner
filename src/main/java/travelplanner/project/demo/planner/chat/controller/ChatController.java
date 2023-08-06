package travelplanner.project.demo.planner.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import travelplanner.project.demo.planner.chat.dto.ChatRequest;
import travelplanner.project.demo.planner.chat.service.ChatService;

@RestController
@RequiredArgsConstructor
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat/{plannerId}")
    public void sendChat(
            @DestinationVariable Long plannerId,
            ChatRequest request
    ) throws Exception{
        messagingTemplate.convertAndSend("/planner-message/" + plannerId, chatService.sendChat(request, plannerId));
    }

}
