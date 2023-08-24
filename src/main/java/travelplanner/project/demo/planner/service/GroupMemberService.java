package travelplanner.project.demo.planner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;
import travelplanner.project.demo.planner.domain.GroupMember;
import travelplanner.project.demo.planner.domain.GroupMemberType;
import travelplanner.project.demo.planner.domain.Planner;
import travelplanner.project.demo.planner.dto.request.GroupMemberCreateRequest;
import travelplanner.project.demo.planner.dto.request.GroupMemberDeleteRequest;
import travelplanner.project.demo.planner.dto.request.GroupMemberSearchRequest;
import travelplanner.project.demo.planner.dto.response.GroupMemberResponse;
import travelplanner.project.demo.planner.dto.response.GroupMemberSearchResponse;
import travelplanner.project.demo.planner.repository.GroupMemberRepository;
import travelplanner.project.demo.planner.repository.PlannerRepository;

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


    // 그룹 멤버 검색
    public GroupMemberSearchResponse searchMember (GroupMemberSearchRequest request) {

            Optional<Member> member = memberRepository.findByEmail(request.getEmail());
                    member.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

            Profile profile = profileRepository.findProfileByMemberId(member.get().getId());

            GroupMemberSearchResponse response = new GroupMemberSearchResponse();
            response.setProfileImageUrl(profile.getProfileImgUrl());
            response.setEmail(member.get().getEmail());
            response.setUserNickname(member.get().getUserNickname());

            return response;

    }


    // 그룹 멤버 추가
    @Transactional
    public GroupMemberResponse addGroupMember(GroupMemberCreateRequest request, Long plannerId) {

        // 그룹멤버 찾기
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());
        Profile profile = profileRepository.findProfileByMemberId(member.get().getId());

        // 플래너 아이디에 해당하는 그룹 멤버 리스트 조회
        List<GroupMember> groupMembers = groupMemberRepository.findGroupMemberByPlannerId(plannerId);


        if (groupMembers.stream().noneMatch(gm -> gm.getEmail().equals(member.get().getEmail()))) {

                // 그룹 멤버에 저장할 플래너 조회
                Planner planner = plannerRepository.findPlannerById(plannerId);

                GroupMember groupMember = GroupMember.builder()
                        .email(member.get().getEmail())
                        .userNickname(member.get().getUserNickname())
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

                GroupMemberResponse response = GroupMemberResponse.builder()
                        .groupMemberId(groupMember.getId())
                        .nickname(groupMember.getUserNickname())
                        .profileImageUrl(groupMember.getProfile().getProfileImgUrl())
                        .role(groupMember.getGroupMemberType())
                        .email(groupMember.getEmail())
                        .build();

                return response;

        }

        throw new ApiException(ErrorType.GROUP_MEMBER_ALREADY_EXIST);
    }


    // 그룹 멤버 삭제
    @Transactional
    public void deleteGroupMember(GroupMemberDeleteRequest request) {

        GroupMember groupMember = groupMemberRepository.findGroupMemberById(request.getGroupMemberId());
        groupMemberRepository.delete(groupMember);
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
