package travelplanner.project.demo.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.chat.dto.ChatRequest;
import travelplanner.project.demo.chat.dto.ChatResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {


    public final MemberRepository memberRepository;

    public Object createChat(ChatRequest request) {
        ChatResponse response = new ChatResponse();
        response.setMessage(request.getMessage());

        //이메일이 일치하는 유저 정보
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());
        response.setUserNickname(member.get().getUserNickname());
        response.setEmail(member.get().getEmail());
        return response;
    }
}
