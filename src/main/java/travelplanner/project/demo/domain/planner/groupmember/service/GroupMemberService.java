package travelplanner.project.demo.domain.planner.groupmember.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.member.repository.MemberRepository;
import travelplanner.project.demo.domain.planner.groupmember.dto.response.GroupMemberDeleteResponse;
import travelplanner.project.demo.domain.planner.groupmember.repository.GroupMemberRepository;
import travelplanner.project.demo.domain.planner.groupmember.domain.GroupMember;
import travelplanner.project.demo.domain.planner.groupmember.domain.GroupMemberType;
import travelplanner.project.demo.domain.planner.groupmember.dto.request.GroupMemberCreateRequest;
import travelplanner.project.demo.domain.planner.groupmember.dto.request.GroupMemberDeleteRequest;
import travelplanner.project.demo.domain.planner.groupmember.dto.response.GroupMemberResponse;
import travelplanner.project.demo.domain.planner.planner.domain.Planner;
import travelplanner.project.demo.domain.planner.planner.repository.PlannerRepository;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.webSocket.WebSocketErrorController;
import travelplanner.project.demo.domain.profile.domain.Profile;
import travelplanner.project.demo.domain.profile.repository.ProfileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final PlannerRepository plannerRepository;
    private final WebSocketErrorController webSocketErrorController;


    // 그룹 멤버 검색
//    public List<GroupMemberSearchResponse> searchMember (String email) {
//
//        List<Member> memberList = memberRepository.findMemberByEmail(email);
//        if (memberList.isEmpty()) {
//            throw new ApiException(ErrorType.USER_NOT_FOUND);
//        }
//
//        List<GroupMemberSearchResponse> groupMemberSearchResponses = new ArrayList<>();
//        for (Member searchMember : memberList) {
//            GroupMemberSearchResponse groupMemberResponse = GroupMemberSearchResponse.builder()
//                    .profileImageUrl(searchMember.getProfile().getProfileImgUrl())
//                    .email(searchMember.getEmail())
//                    .userId(searchMember.getId())
//                    .userNickname(searchMember.getUserNickname())
//                    .build();
//
//            groupMemberSearchResponses.add(groupMemberResponse);
//        }
//
//        return groupMemberSearchResponses;
//    }


    // 그룹 멤버 추가
    @Transactional
    public GroupMemberResponse addGroupMember(GroupMemberCreateRequest request, Long plannerId) {

        // 그룹멤버 찾기
        Optional<Member> member = memberRepository.findById(request.getUserId());
        Profile profile = profileRepository.findProfileByMemberId(member.get().getId());

        // 플래너 아이디에 해당하는 그룹 멤버 리스트 조회
        List<GroupMember> groupMembers = groupMemberRepository.findGroupMemberByPlannerId(plannerId);


        if (groupMembers.stream().noneMatch(gm -> gm.getUserId().equals(member.get().getId()))) {

                // 그룹 멤버에 저장할 플래너 조회
                Planner planner = plannerRepository.findPlannerById(plannerId);

                GroupMember groupMember = GroupMember.builder()
                        .email(member.get().getEmail())
                        .userNickname(member.get().getUserNickname())
                        .userId(member.get().getId())
                        .groupMemberType(GroupMemberType.MEMBER)
                        .profile(profile)
                        .planner(planner)
                        .build();

                groupMemberRepository.save(groupMember);

//            GroupMemberResponse response = new GroupMemberResponse();
//            response.setGroupMemberId(groupMember.getId());
//            response.setNickname(groupMember.getUserNickname());
//            response.setProfileImageUrl(groupMember.getProfileImageUrl());
//            response.setRole(groupMember.getGroupMemberType().toString());

            return GroupMemberResponse.builder()
                    .groupMemberId(groupMember.getId())
                    .nickname(groupMember.getUserNickname())
                    .userId(groupMember.getUserId())
                    .profileImageUrl(groupMember.getProfile().getProfileImgUrl())
                    .role(groupMember.getGroupMemberType())
                    .email(groupMember.getEmail())
                    .build();

        }

        webSocketErrorController.handleChatMessage(ErrorType.GROUP_MEMBER_ALREADY_EXIST);
        throw new ApiException(ErrorType.GROUP_MEMBER_ALREADY_EXIST);
    }


    // 그룹 멤버 삭제
    @Transactional
    public GroupMemberDeleteResponse deleteGroupMember(GroupMemberDeleteRequest request) {

        GroupMember groupMember = groupMemberRepository.findGroupMemberById(request.getGroupMemberId());

        groupMemberRepository.delete(groupMember);

        return GroupMemberDeleteResponse.builder()
                .groupMemberId(request.getGroupMemberId())
                .build();
    }

    // 플래너 조회 시 해당 채팅 내역 조회
    public List<GroupMemberResponse> getGroupMemberList(Long plannerId) {
        List<GroupMember> groupMemberList = groupMemberRepository.findByPlannerId(plannerId);
        List<GroupMemberResponse> groupMemberResponses = new ArrayList<>();


        for (GroupMember groupMember : groupMemberList) {

            GroupMemberResponse groupMemberResponse = GroupMemberResponse.builder()
                    .groupMemberId(groupMember.getId())
                    .nickname(groupMember.getUserNickname())
                    .profileImageUrl(groupMember.getProfile().getProfileImgUrl())
                    .email(groupMember.getEmail())
                    .role(groupMember.getGroupMemberType())
                    .build();

            groupMemberResponses.add(groupMemberResponse);
        }

        return groupMemberResponses;
    }
}
