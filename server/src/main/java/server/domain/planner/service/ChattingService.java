package server.domain.planner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.planner.dto.request.ChatRequest;
import server.domain.planner.dto.response.ChatResponse;
import server.domain.user.domain.User;
import server.domain.user.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChattingService {

    private final UserRepository userRepository;

    public ChatResponse createChat(ChatRequest request) {

        ChatResponse response = new ChatResponse();
        response.setMessage(request.getMessage());

        Optional<User> user = userRepository.findByUserId(request.getUserId());
        response.setUserNickname(user.get().getUserNickname());

        return response;
    }
}
