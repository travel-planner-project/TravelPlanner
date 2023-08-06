package travelplanner.project.demo.planner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.planner.domain.Calendar;
import travelplanner.project.demo.planner.domain.CalendarEditor;
import travelplanner.project.demo.planner.domain.GroupMember;
import travelplanner.project.demo.planner.domain.Planner;
import travelplanner.project.demo.planner.dto.request.CalendarCreateRequest;
import travelplanner.project.demo.planner.dto.request.CalendarEditRequest;
import travelplanner.project.demo.planner.dto.response.CalendarResponse;
import travelplanner.project.demo.planner.repository.CalendarRepository;
import travelplanner.project.demo.planner.repository.GroupMemberRepository;
import travelplanner.project.demo.planner.repository.PlannerRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final PlannerRepository plannerRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;

    public void createDate(Long plannerId, CalendarCreateRequest createRequest) {

        Planner planner = validatePlannerAndUserAccess(plannerId);

        Calendar buildRequest = Calendar.builder()
                .eachDate(createRequest.getEachDate())
                .planner(planner)
                .build();

        calendarRepository.save(buildRequest);
    }

    public void deleteDate(Long plannerId, Long deleteId){

        // 반환값 무시하고 검증만 함
        Planner planner = validatePlannerAndUserAccess(plannerId);
        Calendar calendar = validateCalendarAccess(planner, deleteId);

        // 캘린더에서 플래너를 갖고 옴
        planner = calendar.getPlanner();

        // 플래너의 calendar 리스트에서 캘린더 제거
        planner.getCalendars().remove(calendar);

        calendarRepository.delete(calendar);
    }

    public void updateDate(Long plannerId, Long updateId, CalendarEditRequest updateRequest) {

        Planner planner = validatePlannerAndUserAccess(plannerId);
        Calendar calendar = validateCalendarAccess(planner, updateId);

        CalendarEditor.CalendarEditorBuilder editorBuilder = calendar.toEditor();
        CalendarEditor calendarEditor = editorBuilder
                .eachDate(updateRequest.getEachDate())
                .build();
        calendar.edit(calendarEditor);
    }

    // 전체 Calendar 조회해서 response를 리스트로 리턴
    public List<CalendarResponse> getCalendarList() {
        List<Calendar> calendarList = calendarRepository.findAll();
        ArrayList<CalendarResponse> calendarResponses = new ArrayList<>();
        for(Calendar calendar : calendarList){

            CalendarResponse calendarResponse = CalendarResponse.builder()
                    .calendarId(calendar.getId())
                    .eachDate(calendar.getEachDate())
                    .createAt(calendar.getCreatedAt())
                    .plannerId(calendar.getPlanner().getId())
                    .build();
            calendarResponses.add(calendarResponse);
        }

        return calendarResponses;
    }

    private Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 사용자의 email 얻기
        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
    }

    // 플래너와 사용자에 대한 검증
    protected Planner validatePlannerAndUserAccess(Long plannerId) {
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new ApiException(ErrorType.PLANNER_NOT_FOUND));

        String currentEmail = getCurrentMember().getEmail();
        List<GroupMember> groupMembers =
                groupMemberRepository.findGroupMemberByPlannerId(plannerId);

        if (groupMembers.stream().noneMatch(gm -> gm.getEmail().equals(currentEmail))) {
            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
        }
        return planner;
    }

    // 캘린더에 대한 검증
    protected Calendar validateCalendarAccess(Planner planner, Long updateId) {

        Calendar calendar = calendarRepository.findById(updateId)
                .orElseThrow(() -> new ApiException(ErrorType.DATE_NOT_FOUND));

        if (!planner.getCalendars().contains(calendar)) {
            throw new ApiException(ErrorType.DATE_NOT_AUTHORIZED);
        }
        return calendar;
    }

}
