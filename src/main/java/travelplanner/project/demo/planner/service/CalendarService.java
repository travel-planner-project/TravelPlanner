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
// TODO 현재 로그인한 사람이 플래너의 그룹멤버에 포함되어있는지 확인해야함.
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new ApiException(ErrorType.PLANNER_NOT_FOUND)); // 플래너를 찾을 수 없는 경우 예외 발생

        Calendar buildRequest = Calendar.builder()
                .eachDate(createRequest.getEachDate())
                .planner(planner)
                .build();

        calendarRepository.save(buildRequest);
    }

    public void deleteDate(Long plannerId, Long deleteId){

        Calendar calendar = validateUserAccess(plannerId, deleteId);

        // Planner 객체에서 Calendar 객체를 제거
        Planner planner = calendar.getPlanner();
        planner.getCalendars().remove(calendar);

        calendarRepository.delete(calendar);
    }

    public void updateDate(Long plannerId, Long updateId, CalendarEditRequest updateRequest) {

        Calendar calendar = validateUserAccess(plannerId, updateId);

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

    private Calendar validateUserAccess(Long plannerId, Long updateId) {
        // 조회했을 때 플래너가 존재하지 않을 경우
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new ApiException(ErrorType.PLANNER_NOT_FOUND));

        // 현재 사용자의 이메일 가져오기
        String currentEmail = getCurrentMember().getEmail();
        List<GroupMember> groupMembers =
                groupMemberRepository.findGroupMemberByPlannerId(plannerId);

        // 현재 사용자가 그룹 멤버에 포함되어 있는지 확인
        if (groupMembers.stream().noneMatch(gm -> gm.getEmail().equals(currentEmail))) {
            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
        }

        // 조회했을 때 캘린더가 존재하지 않을 경우
        Calendar calendar = calendarRepository.findById(updateId)
                .orElseThrow(() -> new ApiException(ErrorType.DATE_NOT_FOUND));

        // 캘린더가 해당 플래너에 속해 있는지 검증
        if (!planner.getCalendars().contains(calendar)) {
            throw new ApiException(ErrorType.DATE_NOT_AUTHORIZED);
        }
        return calendar;
    }

}
