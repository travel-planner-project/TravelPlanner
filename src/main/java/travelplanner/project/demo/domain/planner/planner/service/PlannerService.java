package travelplanner.project.demo.domain.planner.planner.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.member.repository.MemberRepository;
import travelplanner.project.demo.domain.planner.calender.dto.response.CalendarResponse;
import travelplanner.project.demo.domain.planner.calender.service.CalendarService;
import travelplanner.project.demo.domain.planner.chat.dto.response.ChatResponse;
import travelplanner.project.demo.domain.planner.chat.service.ChatService;
import travelplanner.project.demo.domain.planner.groupmember.repository.GroupMemberRepository;
import travelplanner.project.demo.domain.planner.groupmember.domain.GroupMember;
import travelplanner.project.demo.domain.planner.groupmember.domain.GroupMemberType;
import travelplanner.project.demo.domain.planner.groupmember.dto.response.GroupMemberResponse;
import travelplanner.project.demo.domain.planner.groupmember.service.GroupMemberService;
import travelplanner.project.demo.domain.planner.planner.domain.Planner;
import travelplanner.project.demo.domain.planner.planner.dto.request.PlannerCreateRequest;
import travelplanner.project.demo.domain.planner.planner.dto.request.PlannerDeleteRequest;
import travelplanner.project.demo.domain.planner.planner.dto.response.*;
import travelplanner.project.demo.domain.planner.planner.editor.PlannerEditRequest;
import travelplanner.project.demo.domain.planner.planner.editor.PlannerEditor;
import travelplanner.project.demo.domain.planner.planner.repository.PlannerRepository;
import travelplanner.project.demo.domain.planner.todo.service.ToDoService;
import travelplanner.project.demo.domain.planner.validation.ValidatingService;
import travelplanner.project.demo.domain.planner.todo.dto.response.ToDoResponse;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.AuthUtil;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.domain.profile.domain.Profile;
import travelplanner.project.demo.domain.profile.repository.ProfileRepository;


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
    private final AuthUtil authUtil;

    // planner detail조회에서 캘린더 목록 및 투두 목록을 갖고오기 위해 추가
    private final CalendarService calendarService;
    private final ToDoService toDoService;
    private final ValidatingService validatingService;
    private final ChatService chatService;
    private final GroupMemberService groupMemberService;
    private final TokenUtil tokenUtil;

    // 플래너 리스트
    // ** 여행 그룹의 프로필 사진도 같이 줘야 합니당
    public Page<PlannerListResponse> getPlannerListByUserIdOrEmail(Pageable pageable, Long userId, HttpServletRequest request) {

        // [공통] ==============================================

        List<Planner> planners;

        if (userId == null) {
            // 유저 아이디 값을 보내지 않았을 때, 모든 유저의 플래너 조회
            planners = plannerRepository.findAll();
        } else {
            // 특정 사용자의 플래너 조회
            Member member = memberRepository.findById(userId)
                    .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));
            planners = plannerRepository.findByMemberOrderByIdDesc(member);
        }

        // ==================================================

        // [로그인 / 비로그인 분기] ===================================

        // 로그인한 경우여서 인증을 받을 수 있는 경우에는 아래와 같이 진행
        if (authUtil.authenticationUser(request)) {

            Long currentUserId = authUtil.getCurrentMember(request).getId();
            log.info("userId: " + currentUserId);

            /*
            이메일을 담지 않고 요청: 모든 유저의 플래너를 보여줘야 하며, 현재 로그인한 사용자가 그룹 멤버에 포함된다면 isPrivate가 true여도 보이게 해야 함.
            특정 유저의 이메일을 담고 요청: 해당 유저의 플래너만 보여주되, 현재 로그인한 사용자가 그룹 멤버에 포함된다면 isPrivate가 true여도 보이게 해야 함.
             */

            // 현재 사용자가 그룹 멤버인지 확인
            List<GroupMember> groupMembers = groupMemberRepository.findByUserId(currentUserId);

            // 로그인한 사용자가 속한 모든 그룹의 플래너를 찾음
            for(GroupMember gm : groupMembers) {
                Planner planner = gm.getPlanner();
                // 이 플래너가 이미 사용자의 플래너 리스트에 있는지 확인 후 없다면 추가 (중복 제거)
                if(!planners.contains(planner)) {
                    planners.add(planner);
                }
            }

            // 현재 사용자가 그룹 멤버가 아닌 경우 isPrivate이 true인 플래너를 제거
            planners = planners.stream()
                    .filter(planner -> !planner.getIsPrivate() || groupMembers.stream().anyMatch(gm -> gm.getPlanner().equals(planner)))
                    .collect(Collectors.toList());

        } else { // 비로그인한 유저의 경우 공개 플래너만 모아서 반환

            // isPrivate = true 인 플래너 제거
            planners = planners.stream()
                    .filter(planner -> !planner.getIsPrivate())
                    .collect(Collectors.toList());
        }

        // ==================================================

        // [공통] 페이지 처리 ======================================

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), planners.size());
        Page<Planner> plannerPage = new PageImpl<>(planners.subList(start, end), pageable, planners.size());

        return plannerPage.map(PlannerListResponse::new);
    }

    // TODO 여행 그룹의 정보도 같이 줘야 합니다. (프로필 사진, 닉네임, 인덱스, 타입)
    //
    public PlannerDetailResponse getPlannerDetailByOrderAndEmail(Long plannerId, HttpServletRequest request) {

        // ===================================== 공통 =============================================
        // 접근 권한 확인
        // 만약, 플래너가 isPrivate == false 인 경우, 그룹멤버가 아니더라도 모든 사람이 볼 수 있어야 합니다.
        // 만약, 프랠너가 isPrivate == true 인 경우, 그룹멤버만 볼 수 있어야 합니다.
        Planner planner = plannerRepository.findPlannerById(plannerId);

        // Planner에 해당하는 캘린더 리스트를 가져옴
        List<CalendarResponse> calendarResponses = calendarService.getCalendarList(planner.getId());

        // 각 캘린더에 해당하는 투두 리스트를 가져와 CalendarResponse에 추가

        List<CalendarResponse> updatedCalendarResponses = new ArrayList<>();
        for (CalendarResponse calendarResponse : calendarResponses) {
            List<ToDoResponse> toDoResponses = toDoService.getScheduleItemList(calendarResponse.getDateId());
            CalendarResponse updatedCalendarResponse = CalendarResponse.builder()
                    .dateId(calendarResponse.getDateId())
                    .dateTitle(calendarResponse.getDateTitle())
                    .createAt(calendarResponse.getCreateAt())
                    .plannerId(calendarResponse.getPlannerId())
                    .scheduleItemList(toDoResponses)
                    .build();
            updatedCalendarResponses.add(updatedCalendarResponse);
        }

        // 플래너에 해당하는 채팅 리스트를 가져옴
        List<ChatResponse> chatResponses = chatService.getChattingList(planner.getId());

        for (ChatResponse chatRespons : chatResponses) {
            log.info("------------------ chatRespons.getId() = " + chatRespons.getId());
            log.info("------------------ chatRespons.getUserNickname() = " + chatRespons.getUserNickname());
            log.info("------------------ chatRespons.getMessage() = " + chatRespons.getMessage());
            log.info("------------------ chatRespons.getUserId() = " + chatRespons.getUserId());
        }

        // 플래너에 해당하는 그룹멤버를 가져옴
        List<GroupMemberResponse> groupMemberResponses = groupMemberService.getGroupMemberList(planner.getId());

//        log.info("------------------email : " + authUtil.getCurrentMember().getEmail());
        // 현재 유저의 이메일을 가져옴 (비회원일 경우 null)
//      ======================================= 공통 로직 종료 ===================================

//      ========  회원 분기 =========

        // 로그인한 경우여서 인증을 받을 수 있는 경우에는 아래와 같이 진행
        if (authUtil.authenticationUser(request)) {
//            String currentEmail = authUtil.getCurrentMember().getEmail();
            if (planner.getIsPrivate()) {
                planner = validatingService.validatePlannerAndUserAccess(request, plannerId); // 그룹멤버만 볼 수 있도록 하는 메서드
            }

//            if (authUtil.isGroupMember(currentEmail, plannerId)) {
//                log.info("---------------회원이며 그룹 멤버다.");
            return PlannerDetailAuthorizedResponse.builder()
                    .plannerId(planner.getId())
                    .planTitle(planner.getPlanTitle())
                    .isPrivate(planner.getIsPrivate())
                    .startDate(planner.getStartDate())
                    .endDate(planner.getEndDate())
                    .calendars(updatedCalendarResponses)
                    .groupMemberList(groupMemberResponses)
                    .chattings(chatResponses)
                    .build();
//            }
//            log.info("---------------회원이지만 그룹 멤버에 포함되지 않는다.");
        }
        log.info("---------------채팅이 없는 response");
        return PlannerDetailUnauthorizedResponse.builder()
                .plannerId(planner.getId())
                .planTitle(planner.getPlanTitle())
                .isPrivate(planner.getIsPrivate())
                .startDate(planner.getStartDate())
                .endDate(planner.getEndDate())
                .calendars(updatedCalendarResponses)
                .groupMemberList(groupMemberResponses)
                .build();
    }

    @Transactional
    //플래너 삭제
    public void deletePlanner(HttpServletRequest request, PlannerDeleteRequest plannerDeleteRequest) {


        Long plannerId = plannerDeleteRequest.getPlannerId();
        log.info("Received delete request for plannerId: {}", plannerDeleteRequest.getPlannerId());

        // 조회했을 때 플래너가 존재하지 않을 경우
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new ApiException(ErrorType.PAGE_NOT_FOUND));

        Member currentMember = authUtil.getCurrentMember(request);


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
    public PlannerCreateResponse createPlanner(HttpServletRequest request, PlannerCreateRequest plannerCreateRequest) {

        log.info("request.getPlanTitle() = {}", plannerCreateRequest.getPlanTitle());
        log.info("request.getIsPrivate() = {}", plannerCreateRequest.getIsPrivate());
        log.info("request.getPlanTitle().getClass() = {}", plannerCreateRequest.getPlanTitle().getClass());

        Planner createPlanner = Planner.builder()
                .planTitle(plannerCreateRequest.getPlanTitle())
                .isPrivate(plannerCreateRequest.getIsPrivate())
                .member(authUtil.getCurrentMember(request))
                // todo 이곳 말고 캘린더를 생성하는 서비스에서 플래너 sratdate, enddate 업데이트해주기
                .build();

        plannerRepository.save(createPlanner);

        // 플래너 만든 사람은 HOST 로 처음에 들어가 있어야 함
        // 플래너 만든 사람은 현재 로그인 한 사람
        Member member = authUtil.getCurrentMember(request);
        Profile profile = profileRepository.findProfileByMemberId(member.getId());

        GroupMember groupMember = GroupMember.builder()
                .email(member.getEmail())
                .groupMemberType(GroupMemberType.HOST)
                .userNickname(member.getUserNickname())
                .userId(member.getId())
                .planner(createPlanner)
                .profile(profile)
                .build();

        groupMemberRepository.save(groupMember);

//        PlannerCreateResponse plannerCreateResponse = new PlannerCreateResponse();
//        plannerCreateResponse.setPlannerId(createPlanner.getId());
//        plannerCreateResponse.setPlanTitle(createPlanner.getPlanTitle());
//        plannerCreateResponse.setIsPrivate(createPlanner.getIsPrivate());

        PlannerCreateResponse plannerCreateResponse = PlannerCreateResponse.builder()
                .plannerId(createPlanner.getId())
                .planTitle(createPlanner.getPlanTitle())
                .isPrivate(createPlanner.getIsPrivate())
                .build();

        return plannerCreateResponse;
    }

    @Transactional
    public void updatePlanner(HttpServletRequest request, PlannerEditRequest plannerEditRequest) {

        Long plannerId = plannerEditRequest.getPlannerId();

        // 조회했을 때 플래너가 존재하지 않을 경우
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new ApiException(ErrorType.PAGE_NOT_FOUND));

        Member currentMember = authUtil.getCurrentMember(request);

        // 플래너를 생성한 사람이 아닐 경우
        if (!planner.getMember().getId().equals(currentMember.getId())) {

            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
        }

        PlannerEditor.PlannerEditorBuilder editorBuilder = planner.toEditor();
        PlannerEditor plannerEditor = editorBuilder
                .planTitle(plannerEditRequest.getPlanTitle())
                .isPrivate(plannerEditRequest.getIsPrivate())
                .build();
        planner.edit(plannerEditor);
    }
}

