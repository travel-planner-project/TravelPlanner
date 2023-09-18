package travelplanner.project.demo.domain.message.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.domain.message.domain.Message;
import travelplanner.project.demo.domain.message.dto.request.MessageSendRequest;
import travelplanner.project.demo.domain.message.service.MessageService;

import java.util.List;
import java.util.Map;

@Tag(name = "Message", description = "쪽지 API")
@Slf4j
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    // 메세지 리스트
    @GetMapping("")
    public Map<Long, List<Message>> getMessageList(HttpServletRequest request) {
        return messageService.getMessageList(request);
    }

    // 메세지 보내기
    @PostMapping("")
    public void sendMessage (@RequestBody MessageSendRequest request, HttpServletRequest servletRequest) {
        messageService.sendMessage(request, servletRequest);
    }
}
