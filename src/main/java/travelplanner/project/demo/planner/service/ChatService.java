package travelplanner.project.demo.planner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.planner.repository.ChattingRepository;
import travelplanner.project.demo.planner.domain.Chatting;
import travelplanner.project.demo.planner.dto.request.ChatRequest;
import travelplanner.project.demo.planner.dto.response.ChatResponse;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;
import travelplanner.project.demo.planner.domain.Planner;
import travelplanner.project.demo.planner.repository.PlannerRepository;

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
    private final PlannerRepository plannerRepository;

    @Transactional
    public ChatResponse sendChat(ChatRequest request, Long plannerId) {
        
        // 유저 정보
        Member member = memberRepository.findById(request.getUserId())
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        // 프로필
        Profile profile = profileRepository.findProfileByMemberId(member.getId());

        // 플래너
        Planner planner = plannerRepository.findPlannerById(plannerId);

        // 새로운 채팅 메시지 생성
        Chatting chatting = Chatting.builder()
                .userNickname(member.getUserNickname())
                .profileImgUrl(profile.getProfileImgUrl())
                .message(request.getMessage())
                .createdAt(LocalDateTime.now())
                .planner(planner)
                .build();

        chattingRepository.save(chatting);

        // 채팅 리스폰스
        ChatResponse chatResponse = new ChatResponse();

        chatResponse.setId(chatting.getId());
        chatResponse.setMessage(chatting.getMessage());
        chatResponse.setUserNickname(chatting.getUserNickname());
        chatResponse.setProfileImgUrl(chatting.getProfileImgUrl());

        return chatResponse;
    }

    // 플래너 조회 시 해당 채팅 내역 조회
    public List<ChatResponse> getChattingList(Long plannerId) {
        List<Chatting> chattings= chattingRepository.findByPlannerId(plannerId);
        ArrayList<ChatResponse> chatResponses = new ArrayList<>();

        for(Chatting chatting : chattings){
            ChatResponse chatResponse = ChatResponse.builder()
                    .id(chatting.getId())
                    .userNickname(chatting.getUserNickname())
                    .profileImgUrl(chatting.getProfileImgUrl())
                    .message(chatting.getMessage())
                    .build();
            chatResponses.add(chatResponse);
        }
        return chatResponses;
    }
}
