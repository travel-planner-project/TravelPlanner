package travelplanner.project.demo.domain.message.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import travelplanner.project.demo.domain.message.domain.Message;
import travelplanner.project.demo.domain.message.dto.request.MessageSendRequest;
import travelplanner.project.demo.domain.message.service.MessageService;
import travelplanner.project.demo.global.exception.ApiExceptionResponse;

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
    @Operation(summary = "쪽지 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쪽지 리스트 조회 성공")
    })
    @GetMapping("")
    public Map<Long, List<Message>> getMessageList(HttpServletRequest request) {
        return messageService.getMessageList(request);
    }

    // 메세지 보내기
    @Operation(summary = "쪽지 보내기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쪽지 보내기 조회 성공"),
            @ApiResponse(responseCode = "404", description = "로그인한 유저와 메세지를 보내는 유저가 일치하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
    })
    @PostMapping("")
    public void sendMessage (@RequestBody MessageSendRequest request, HttpServletRequest servletRequest) {
        messageService.sendMessage(request, servletRequest);
    }
}
