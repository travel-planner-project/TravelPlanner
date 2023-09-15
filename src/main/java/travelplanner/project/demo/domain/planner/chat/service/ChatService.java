package travelplanner.project.demo.domain.planner.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.member.repository.MemberRepository;
import travelplanner.project.demo.domain.planner.chat.dto.request.ChatRequest;
import travelplanner.project.demo.domain.planner.chat.dto.response.ChatResponse;
import travelplanner.project.demo.domain.planner.chat.domain.Chatting;
import travelplanner.project.demo.domain.planner.chat.repository.ChattingRepository;
import travelplanner.project.demo.domain.planner.validation.ValidatingService;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.webSocket.WebSocketErrorController;
import travelplanner.project.demo.domain.profile.domain.Profile;
import travelplanner.project.demo.domain.profile.repository.ProfileRepository;
import travelplanner.project.demo.domain.planner.planner.domain.Planner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {


    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final ChattingRepository chattingRepository;
    private final WebSocketErrorController webSocketErrorController;
    private final ValidatingService validatingService;
    @Transactional
    public ChatResponse sendChat(String accessToken, ChatRequest request, Long plannerId) {
        
        // 유저 정보
        Member member = memberRepository.findById(request.getUserId())
                /*.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));*/
                .orElse(null);
        if(member == null){
            webSocketErrorController.handleChatMessage(ErrorType.USER_NOT_FOUND);
            throw new ApiException(ErrorType.USER_NOT_FOUND);
        }

        // 플래너와 그룹 멤버 검증 후 플래너 리턴
        Planner planner = validatingService.validatePlannerAndUserAccessForWebSocket(accessToken, plannerId);

        // 프로필
        Profile profile = profileRepository.findProfileByMemberId(member.getId());


        // 새로운 채팅 메시지 생성
        Chatting chatting = Chatting.builder()
                .userId(member.getId())
                .userNickname(member.getUserNickname())
                .profileImgUrl(profile.getProfileImgUrl())
                .message(request.getMessage())
                .createdAt(LocalDateTime.now())
                .planner(planner)
                .build();

        chattingRepository.save(chatting);

        ChatResponse response = ChatResponse.builder()
                .id(chatting.getId())
                .userId(chatting.getUserId())
                .userNickname(chatting.getUserNickname())
                .profileImgUrl(chatting.getProfileImgUrl())
                .message(chatting.getMessage())
                .build();
        return response;
    }

    // 플래너 조회 시 해당 채팅 내역 조회
    public List<ChatResponse> getChattingList(Long plannerId) {
        List<Chatting> chattings= chattingRepository.findByPlannerId(plannerId);
        ArrayList<ChatResponse> chatResponses = new ArrayList<>();

        for(Chatting chatting : chattings){
            ChatResponse chatResponse = ChatResponse.builder()
                    .id(chatting.getId())
                    .userId(chatting.getUserId())
                    .userNickname(chatting.getUserNickname())
                    .profileImgUrl(chatting.getProfileImgUrl())
                    .message(chatting.getMessage())
                    .build();
            chatResponses.add(chatResponse);
        }
        return chatResponses;
    }
}
