package server.domain.planner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import server.domain.planner.dto.request.ChatRequest;
import server.domain.planner.service.ChattingService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChattingService chattingService;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/chat/{plannerId}")
    public void chat(
            @DestinationVariable("plannerId") Long plannerId
            , ChatRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of(
                        "type", "chat",
                        "msg", chattingService.createChat(request)
                )
        );
    }
}
