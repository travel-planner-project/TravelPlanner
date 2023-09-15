package travelplanner.project.demo.domain.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.member.repository.MemberRepository;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSearchService {

    private final MemberRepository memberRepository;

    public List<MemberSearchResponse> searchMember (String email) {

        List<Member> memberList = memberRepository.findMemberByEmail(email);
        if (memberList.isEmpty()) {
            throw new ApiException(ErrorType.USER_NOT_FOUND);
        }

        List<MemberSearchResponse> groupMemberSearchResponses = new ArrayList<>();
        for (Member searchMember : memberList) {
            MemberSearchResponse groupMemberResponse = MemberSearchResponse.builder()
                    .profileImageUrl(searchMember.getProfile().getProfileImgUrl())
                    .email(searchMember.getEmail())
                    .userId(searchMember.getId())
                    .userNickname(searchMember.getUserNickname())
                    .build();

            groupMemberSearchResponses.add(groupMemberResponse);
        }

        return groupMemberSearchResponses;
    }
}
