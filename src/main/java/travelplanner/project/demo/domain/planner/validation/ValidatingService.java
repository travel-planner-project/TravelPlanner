package travelplanner.project.demo.domain.planner.validation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.domain.planner.calender.repository.CalendarRepository;
import travelplanner.project.demo.domain.planner.groupmember.repository.GroupMemberRepository;
import travelplanner.project.demo.domain.planner.planner.repository.PlannerRepository;
import travelplanner.project.demo.domain.planner.todo.repository.ToDoRepository;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.webSocket.WebSocketErrorController;
import travelplanner.project.demo.global.util.AuthUtil;
import travelplanner.project.demo.domain.planner.calender.domain.Calendar;
import travelplanner.project.demo.domain.planner.groupmember.domain.GroupMember;
import travelplanner.project.demo.domain.planner.planner.domain.Planner;
import travelplanner.project.demo.domain.planner.todo.domain.ToDo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidatingService {

    private final PlannerRepository plannerRepository;
    private final CalendarRepository calendarRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final ToDoRepository toDoRepository;
    private final AuthUtil authUtil;
    private final WebSocketErrorController webSocketErrorController;

    // 플래너와 사용자에 대한 검증
    public Planner validatePlannerAndUserAccess(HttpServletRequest request, Long plannerId) {
        Planner planner = plannerRepository.findById(plannerId)
                /*.orElseThrow(() -> new ApiException(ErrorType.PLANNER_NOT_FOUND));*/
                .orElse(null);
        if(planner == null){
            webSocketErrorController.handleChatMessage(ErrorType.PLANNER_NOT_FOUND);
            throw new ApiException(ErrorType.PLANNER_NOT_FOUND);
        }

        Long userId = authUtil.getCurrentMember(request).getId();
        List<GroupMember> groupMembers =
                groupMemberRepository.findGroupMemberByPlannerId(plannerId);

        if (groupMembers.stream().noneMatch(gm -> gm.getUserId().equals(userId))) {
            webSocketErrorController.handleChatMessage(ErrorType.USER_NOT_FOUND);
            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
        }
        return planner;
    }

    public Planner validatePlannerAndUserAccessForWebSocket(String accessToken, Long plannerId) {
        Planner planner = plannerRepository.findById(plannerId)
                /*.orElseThrow(() -> new ApiException(ErrorType.PLANNER_NOT_FOUND));*/
                .orElse(null);
        if(planner == null){
            webSocketErrorController.handleChatMessage(ErrorType.PLANNER_NOT_FOUND);
            throw new ApiException(ErrorType.PLANNER_NOT_FOUND);
        }

        Long userId = authUtil.getCurrentMemberForWebSocket(accessToken).getId();
        List<GroupMember> groupMembers =
                groupMemberRepository.findGroupMemberByPlannerId(plannerId);

        if (groupMembers.stream().noneMatch(gm -> gm.getUserId().equals(userId))) {
            webSocketErrorController.handleChatMessage(ErrorType.USER_NOT_FOUND);
            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
        }
        return planner;
    }


    // 캘린더에 대한 검증
    public Calendar validateCalendarAccess(Planner planner, Long updateId) {

        Calendar calendar = calendarRepository.findById(updateId)
                /*.orElseThrow(() -> new ApiException(ErrorType.DATE_NOT_FOUND));*/
                .orElse(null);

        if(calendar == null){
            webSocketErrorController.handleChatMessage(ErrorType.DATE_NOT_FOUND);
            throw new ApiException(ErrorType.DATE_NOT_FOUND);
        }

        if (!planner.getCalendars().contains(calendar)) {
            webSocketErrorController.handleChatMessage(ErrorType.DATE_NOT_AUTHORIZED);
            throw new ApiException(ErrorType.DATE_NOT_AUTHORIZED);
        }
        return calendar;
    }

    // 투두에 대한 검증
    public ToDo validateToDoAccess(Calendar calendar, Long updateId) {

        ToDo toDo = toDoRepository.findById(updateId)
                /*.orElseThrow(() -> new ApiException(ErrorType.TODO_NOT_FOUND));*/
                .orElse(null);
        if(toDo == null){
            webSocketErrorController.handleChatMessage(ErrorType.TODO_NOT_FOUND);
            throw new ApiException(ErrorType.TODO_NOT_FOUND);
        }
        if (!calendar.getScheduleItemList().contains(toDo)) {
            webSocketErrorController.handleChatMessage(ErrorType.TODO_NOT_AUTHORIZED);
            throw new ApiException(ErrorType.TODO_NOT_AUTHORIZED);
        }
        return toDo;
    }
}
