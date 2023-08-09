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
import travelplanner.project.demo.planner.domain.GroupMember;
import travelplanner.project.demo.planner.domain.Planner;
import travelplanner.project.demo.planner.domain.ToDo;
import travelplanner.project.demo.planner.repository.CalendarRepository;
import travelplanner.project.demo.planner.repository.GroupMemberRepository;
import travelplanner.project.demo.planner.repository.PlannerRepository;
import travelplanner.project.demo.planner.repository.ToDoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidatingService {

    private final PlannerRepository plannerRepository;
    private final CalendarRepository calendarRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final MemberRepository memberRepository;
    private final ToDoRepository toDoRepository;

    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 사용자의 email 얻기
        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
    }

    // 플래너와 사용자에 대한 검증
    public Planner validatePlannerAndUserAccess(Long plannerId) {
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
    public Calendar validateCalendarAccess(Planner planner, Long updateId) {

        Calendar calendar = calendarRepository.findById(updateId)
                .orElseThrow(() -> new ApiException(ErrorType.DATE_NOT_FOUND));

        if (!planner.getCalendars().contains(calendar)) {
            throw new ApiException(ErrorType.DATE_NOT_AUTHORIZED);
        }
        return calendar;
    }

    // 캘린더에 대한 검증
    public ToDo validateToDoAccess(Calendar calendar, Long updateId) {

        ToDo toDo = toDoRepository.findById(updateId)
                .orElseThrow(() -> new ApiException(ErrorType.TODO_NOT_FOUND));

        if (!calendar.getScheduleItemList().contains(toDo)) {
            throw new ApiException(ErrorType.TODO_NOT_AUTHORIZED);
        }
        return toDo;
    }
}
