package server.domain.planner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.planner.dto.request.ChatRequest;
import server.domain.planner.dto.response.ChatResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChattingService {

    public ChatResponse createChat(ChatRequest request) {

        ChatResponse response = new ChatResponse();
        response.setMessage(request.getMessage());

        return response;
    }
}
