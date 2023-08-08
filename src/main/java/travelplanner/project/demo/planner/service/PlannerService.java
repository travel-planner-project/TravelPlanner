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
import travelplanner.project.demo.planner.dto.response.*;
import travelplanner.project.demo.planner.repository.GroupMemberRepository;
import travelplanner.project.demo.planner.repository.PlannerRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    // planner detail조회에서 캘린더 목록 및 투두 목록을 갖고오기 위해 추가
    private final CalendarService calendarService;
    private final ToDoService toDoService;
    private final ValidatingService validatingService;

    // 플래너 리스트
    // ** 여행 그룹의 프로필 사진도 같이 줘야 합니당
    public Page<PlannerListResponse> getPlannerListByUserIdOrEmail(Pageable pageable, String email) {
        String currentEmail = getCurrentMember().getEmail();
        List<Planner> planners;

        /*
        이메일을 담지 않고 요청: 모든 유저의 플래너를 보여줘야 하며, 현재 로그인한 사용자가 그룹 멤버에 포함된다면 isPrivate가 true여도 보이게 해야 함.
        특정 유저의 이메일을 담고 요청: 해당 유저의 플래너만 보여주되, 현재 로그인한 사용자가 그룹 멤버에 포함된다면 isPrivate가 true여도 보이게 해야 함.
         */

        // 현재 사용자가 그룹 멤버인지 확인
        List<GroupMember> groupMembers = groupMemberRepository.findByEmail(currentEmail);

        if (email == null) {
            // 이메일 값을 보내지 않았을 때, 모든 유저의 플래너 조회
            planners = plannerRepository.findAll();
        } else {
            // 특정 사용자의 플래너 조회
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));
            planners = plannerRepository.findByMember(member);
        }

        // 현재 사용자가 그룹 멤버가 아닌 경우 isPrivate이 true인 플래너를 제거
        planners = planners.stream()
                .filter(planner -> !planner.getIsPrivate() || groupMembers.stream().anyMatch(gm -> gm.getPlanner().equals(planner)))
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), planners.size());
        Page<Planner> plannerPage = new PageImpl<>(planners.subList(start, end), pageable, planners.size());

        return plannerPage.map(PlannerListResponse::new);
    }

    // TODO 여행 그룹의 정보도 같이 줘야 합니다. (프로필 사진, 닉네임, 인덱스, 타입)
    //
    public PlannerDetailResponse getPlannerDetailByOrderAndEmail(Long plannerId) {


        // 접근 권한 확인
        Planner planner = validatingService.validatePlannerAndUserAccess(plannerId);


        // Planner에 해당하는 캘린더 리스트를 가져옴
        List<CalendarResponse> calendarResponses = calendarService.getCalendarList(planner.getId());

        // 각 캘린더에 해당하는 투두 리스트를 가져와 CalendarResponse에 추가

        List<CalendarResponse> updatedCalendarResponses = new ArrayList<>();
        for (CalendarResponse calendarResponse : calendarResponses) {
            List<ToDoResponse> toDoResponses = toDoService.getToDoList(calendarResponse.getCalendarId());
            CalendarResponse updatedCalendarResponse = CalendarResponse.builder()
                    .calendarId(calendarResponse.getCalendarId())
                    .eachDate(calendarResponse.getEachDate())
                    .createAt(calendarResponse.getCreateAt())
                    .plannerId(calendarResponse.getPlannerId())
                    .toDoList(toDoResponses)
                    .build();
            updatedCalendarResponses.add(updatedCalendarResponse);
        }

        PlannerDetailResponse response = PlannerDetailResponse.builder()
                .plannerId(planner.getId())
                .planTitle(planner.getPlanTitle())
                .isPrivate(planner.getIsPrivate())
                .startDate(planner.getStartDate())
                .endDate(planner.getEndDate())
                .calendars(updatedCalendarResponses)
                .build();

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


        // 플래너를 생성한 사람일 경우
        // 플래너를 만든사람 == currentUser : 플래너를 아예 삭제한다.
        if (planner.getMember().getId().equals(currentMember.getId())) {

            // 이때 플래너와 관련된 그룹멤버도 전부 삭제
            groupMemberRepository.deleteAllByPlannerId(plannerId);
            plannerRepository.delete(planner);

        } else {
            // 플래너를 생성한 사람이 아닐 경우
            // 플래너를 만든사람 /= currentUser: 삭제 권한 없음
            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
        }
    }

    @Transactional
    public PlannerCreateResponse createPlanner(PlannerCreateRequest request) {

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

        PlannerCreateResponse plannerCreateResponse = new PlannerCreateResponse();
        plannerCreateResponse.setPlannerId(createPlanner.getId());
        plannerCreateResponse.setPlanTitle(createPlanner.getPlanTitle());
        plannerCreateResponse.setIsPrivate(createPlanner.getIsPrivate());

        return plannerCreateResponse;
    }

    @Transactional
    public void updatePlanner(PlannerEditRequest request) {

        Long plannerId = request.getPlannerId();

        // 조회했을 때 플래너가 존재하지 않을 경우
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new ApiException(ErrorType.PAGE_NOT_FOUND));

        Member currentMember = getCurrentMember();

        // 플래너를 생성한 사람이 아닐 경우
        if (!planner.getMember().getId().equals(currentMember.getId())) {

            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
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

