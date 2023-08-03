package travelplanner.project.demo.planner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.exception.ExceptionType;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;
import travelplanner.project.demo.planner.domain.GroupMember;
import travelplanner.project.demo.planner.domain.GroupMemberType;
import travelplanner.project.demo.planner.domain.TravelGroup;
import travelplanner.project.demo.planner.dto.request.GroupMemberCreateRequest;
import travelplanner.project.demo.planner.dto.request.GroupMemberSearchRequest;
import travelplanner.project.demo.planner.dto.response.GroupMemberCreateResponse;
import travelplanner.project.demo.planner.dto.response.GroupMemberSearchResponse;
import travelplanner.project.demo.planner.repository.GroupMemberRepository;
import travelplanner.project.demo.planner.repository.TravelGroupRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupMemberService {

    private GroupMemberRepository groupMemberRepository;
    private TravelGroupRepository travelGroupRepository;
    private MemberRepository memberRepository;
    private ProfileRepository profileRepository;

    // 그룹 멤버 검색
    public GroupMemberSearchResponse searchMember (GroupMemberSearchRequest request) {

        Optional<Member> member = memberRepository.findByEmail(request.getEmail());
        Profile profile = profileRepository.findProfileByMemberId(member.get().getId());

        if (member.isEmpty()) {

            throw new Exception(ExceptionType.USER_NOT_FOUND);
        }

        GroupMemberSearchResponse response = new GroupMemberSearchResponse();
        response.setProfileImageUrl(profile.getProfileImgUrl());
        response.setEmail(member.get().getEmail());
        response.setUserNickname(member.get().getUserNickname());

        return response;
    }


    // 그룹 멤버 추가
    public GroupMemberCreateResponse addGroupMember(GroupMemberCreateRequest request, Long plannerId) {

        // 그룹멤버 찾기
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());
        Profile profile = profileRepository.findProfileByMemberId(member.get().getId());

        if (groupMemberRepository.findGroupMemberById(member.get().getId()) == null) {

            GroupMember groupMember = GroupMember.builder()
                    .id(member.get().getId())
                    .userNickname(member.get().getUserNickname())
                    .profileImageUrl(profile.getProfileImgUrl())
                    .groupMemberType(GroupMemberType.MEMBER)
                    .build();

            groupMemberRepository.save(groupMember);

            TravelGroup travelGroup = travelGroupRepository.findTravelGroupByPlannerId(plannerId);
            travelGroup.createGroupMember(groupMember);
            travelGroupRepository.save(travelGroup);

            GroupMemberCreateResponse response = new GroupMemberCreateResponse();
            response.setGroupMemberId(groupMember.getId());
            response.setNickname(groupMember.getUserNickname());
            response.setProfileImageUrl(groupMember.getProfileImageUrl());
            response.setRole(groupMember.getGroupMemberType().toString());

            return response;
        }

        throw new Exception(ExceptionType.GROUP_MEMBER_ALREADY_EXIST);
    }


    // 그룹 멤버 삭제
    public void deleteGroupMember(Long plannerId, Long groupMemberId) {

        TravelGroup travelGroup = travelGroupRepository.findTravelGroupByPlannerId(plannerId);

        List<GroupMember> groupMembers = travelGroup.getGroupMembers();

        for (GroupMember groupMember : groupMembers) {

            if (groupMember.getId().equals(groupMemberId)) {
                travelGroup.deleteGroupMember(groupMember);
                break;
            }
        }

        travelGroupRepository.save(travelGroup);
    }
}
