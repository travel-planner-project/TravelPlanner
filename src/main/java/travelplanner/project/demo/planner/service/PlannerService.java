package travelplanner.project.demo.planner.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;
import travelplanner.project.demo.planner.domain.*;
import travelplanner.project.demo.planner.dto.request.PlannerCreateRequest;
import travelplanner.project.demo.planner.dto.request.PlannerDeleteRequest;
import travelplanner.project.demo.planner.dto.request.PlannerEditRequest;
import travelplanner.project.demo.planner.dto.response.PlannerDetailResponse;
import travelplanner.project.demo.planner.dto.response.PlannerListResponse;
import travelplanner.project.demo.planner.repository.GroupMemberRepository;
import travelplanner.project.demo.planner.repository.PlannerRepository;


import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
//@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
public class PlannerService {

    private final MemberRepository memberRepository;
    private final PlannerRepository plannerRepository;
    private final ProfileRepository profileRepository;
    private final GroupMemberRepository groupMemberRepository;

    // 플래너 리스트
    // ** 여행 그룹의 프로필 사진도 같이 줘야 합니당
    public Page<PlannerListResponse> getPlannerListByUserId (Pageable pageable){

        // 현재 사용자의 이메일을 가져옴
        String currentEmail = getCurrentMember().getEmail();

        // 그룹 멤버 리포지토리에서 현재 사용자의 이메일과 일치하는 모든 그룹 멤버를 찾음
        List<GroupMember> groupMembersWithCurrentEmail = groupMemberRepository.findByEmail(currentEmail);

        // 각 그룹 멤버가 속한 플래너를 가져와 결과 리스트에 담음
        List<Planner> plannersOfCurrentUser = new ArrayList<>();
        for (GroupMember groupMember : groupMembersWithCurrentEmail) {
            Planner planner = groupMember.getPlanner();
            plannersOfCurrentUser.add(planner);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), plannersOfCurrentUser.size());
        Page<Planner> plannerPage = new PageImpl<>(plannersOfCurrentUser.subList(start, end), pageable, plannersOfCurrentUser.size());

        return plannerPage.map(PlannerListResponse::new);
    }

    // TODO 여행 그룹의 정보도 같이 줘야 합니다. (프로필 사진, 닉네임, 인덱스, 타입)
    //
    public PlannerDetailResponse getPlannerDetailById(Long plannerId) {
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new ApiException(ErrorType.PAGE_NOT_FOUND));

        // DTO에 값을 채워넣기
        PlannerDetailResponse response = PlannerDetailResponse.builder()
                .plannerId(planner.getId())
                .planTitle(planner.getPlanTitle())
                .isPrivate(planner.getIsPrivate())
                .startDate(planner.getStartDate())
                .endDate(planner.getEndDate())
                .build();

        // TODO: 그룹멤버 정보와 채팅부분을 추가로 채워넣기

        return response;
    }

    @Transactional
    //플래너 삭제
    public void deletePlanner(PlannerDeleteRequest plannerDeleteRequest) {


        Long plannerId = plannerDeleteRequest.getPlannerId();
        log.info("Received delete request for plannerId: {}", plannerDeleteRequest.getPlannerId());

        // 조회했을 때 플래너가 존재하지 않을 경우
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new ApiException(ErrorType.PAGE_NOT_FOUND));

        Member currentMember = getCurrentMember();

        // 플래너를 생성한 사람이 아닐 경우
        // 플래너를 만든사람 /= currentUser: 플래너의 그룹에서 currentUser 제거
        if (!planner.getMember().getId().equals(currentMember.getId())) {

            // 플래너 인덱스를 기준으로 그룹 멤버들을 가져옴
            List<GroupMember> groupMembers = groupMemberRepository.findGroupMemberByPlannerId(plannerId);

            // 그 그룹멤버들 중에서 현재 유저와 맞는 멤버일 경우 삭제
            for (GroupMember groupMember : groupMembers) {

                if (groupMember.getId().equals(currentMember.getId())) {
                    groupMemberRepository.delete(groupMember);
                    break;
                }
            }

        } else {
            // 플래너를 만든사람 == currentUser : 플래너를 아예 삭제한다.
            // 이때 플래너와 관련된 그룹멤버도 전부 삭제

            groupMemberRepository.deleteAllByPlannerId(plannerId);
            plannerRepository.delete(planner);
        }
    }

    @Transactional
    public void createPlanner(PlannerCreateRequest request) {

        log.info("request.getPlanTitle() = {}", request.getPlanTitle());
        log.info("request.getIsPrivate() = {}", request.getIsPrivate());
        log.info("request.getPlanTitle().getClass() = {}", request.getPlanTitle().getClass());

        Planner createPlanner = Planner.builder()
                .planTitle(request.getPlanTitle())
                .isPrivate(request.getIsPrivate())
                .member(getCurrentMember())
                // todo 이곳 말고 캘린더를 생성하는 서비스에서 플래너 sratdate, enddate 업데이트해주기
                .build();

        plannerRepository.save(createPlanner);

        // 플래너 만든 사람은 HOST 로 처음에 들어가 있어야 함
        // 플래너 만든 사람은 현재 로그인 한 사람
        Member member = getCurrentMember();
        Profile profile = profileRepository.findProfileByMemberId(member.getId());

        GroupMember groupMember = GroupMember.builder()
                .email(member.getEmail())
                .groupMemberType(GroupMemberType.HOST)
                .profileImageUrl(profile.getProfileImgUrl())
                .userNickname(member.getUserNickname())
                .planner(createPlanner)
                .build();

        groupMemberRepository.save(groupMember);
    }

    @Transactional
    public void updatePlanner(PlannerEditRequest request) {

        Long plannerId = request.getPlannerId();

        // 조회했을 때 플래너가 존재하지 않을 경우
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new ApiException(ErrorType.PAGE_NOT_FOUND));

        // TODO 플래너 엔티티를 지울 수 있는지에 대한 자격조건 확인해야함
        Member currentMember = getCurrentMember();

        // 플래너를 생성한 사람이 아닐 경우
        if (!planner.getMember().getId().equals(currentMember.getId())) {

            throw new ApiException(ErrorType.USER_NOT_FOUND);
        }

        PlannerEditor.PlannerEditorBuilder editorBuilder = planner.toEditor();
        PlannerEditor plannerEditor = editorBuilder
                .planTitle(request.getPlanTitle())
                .isPrivate(request.getIsPrivate())
                .build();
        planner.edit(plannerEditor);
    }

    private Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 사용자의 email 얻기
        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
    }
}

