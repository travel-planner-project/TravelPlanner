package travelplanner.project.demo.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.chat.dto.ChatRequest;
import travelplanner.project.demo.chat.dto.ChatResponse;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {


    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public ChatResponse sendChat(ChatRequest request) {
        
        // 유저 정보
        Optional<Member> member = memberRepository.findByEmail(request.getUserId());
        Profile profile = profileRepository.findProfileByMemberId(member.get().getId());

        ChatResponse chatResponse = new ChatResponse();

        chatResponse.setMessage(request.getMessage());
        chatResponse.setUserNickname(member.get().getUserNickname());
        chatResponse.setProfileImgUrl(profile.getProfileImgUrl());

        return chatResponse;
    }
}
