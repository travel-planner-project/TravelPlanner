package travelplanner.project.demo.planner.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.planner.domain.*;
import travelplanner.project.demo.planner.dto.request.ToDoCraeteRequest;
import travelplanner.project.demo.planner.dto.request.ToDoEditRequest;
import travelplanner.project.demo.planner.dto.response.CalendarResponse;
import travelplanner.project.demo.planner.dto.response.ToDoResponse;
import travelplanner.project.demo.planner.repository.CalendarRepository;
import travelplanner.project.demo.planner.repository.ToDoRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final CalendarRepository calendarRepository;
    private final ValidatingService validatingService;

    // 플래너 서비스에서 특정 플래너에 포함된 캘린더 및 투두를 갖고오기 위해 오버로딩
    public List<ToDoResponse> getScheduleItemList(Long calendarId) {

        List<ToDo> scheduleItemList = toDoRepository.findByCalendarId(calendarId);
        ArrayList<ToDoResponse> toDoResponses = new ArrayList<>();
        for (ToDo toDo : scheduleItemList) {
            ToDoResponse toDoResponse = ToDoResponse.builder()
                    .itemId(toDo.getId())
                    .dateId(toDo.getCalendar().getId())
                    .itemTitle(toDo.getItemTitle())
                    .category(toDo.getCategory())
                    .itemTime(toDo.getItemTime())
                    .itemContent(toDo.getItemContent())
                    .isPrivate(toDo.getIsPrivate())
                    .budget(toDo.getBudget())
                    .itemAddress(toDo.getItemAddress())
                    .build();
            toDoResponses.add(toDoResponse);
        }
        return toDoResponses;
    }



    public ToDoResponse createTodo(Long plannerId, Long dateId,
                           ToDoCraeteRequest request) {
        // 플래너와 사용자에 대한 검증
        Planner planner = validatingService.validatePlannerAndUserAccess(plannerId);
        // 캘린더에 대한 검증
        Calendar calendar = validatingService.validateCalendarAccess(planner, dateId);

        ToDo todo = ToDo.builder()
                .calendar(calendar)
                .itemTitle(request.getItemTitle())
                .itemTime(request.getItemTime())
                .category(request.getCategory())
                .itemContent(request.getItemContent())
                .isPrivate(request.getIsPrivate())
                .budget(request.getBudget())
                .itemAddress(request.getItemAddress())
                .build();
        toDoRepository.save(todo);

        return ToDoResponse.builder()
                .dateId(dateId)
                .itemContent(todo.getItemContent())
                .itemId(todo.getId())
                .itemTime(todo.getItemTime())
                .itemTitle(todo.getItemTitle())
                .itemAddress(todo.getItemAddress())
                .category(todo.getCategory())
                .itemContent(todo.getItemContent())
                .budget(todo.getBudget())
                .build();
    }

    @Transactional
    public ToDoResponse editTodo(
            Long plannerId, Long dateId, Long toDoId,
            ToDoEditRequest editRequest
    ) {

        // 플래너와 사용자에 대한 검증
        Planner planner = validatingService.validatePlannerAndUserAccess(plannerId);
        // 캘린더에 대한 검증
        Calendar calendar = validatingService.validateCalendarAccess(planner, dateId);
        // 투두에 대한 검증
        ToDo toDo = validatingService.validateToDoAccess(calendar, toDoId);

        // 수정 로직 시작
        ToDoEditor.ToDoEditorBuilder editorBuilder = toDo.toEditor();
        ToDoEditor toDoEditor = editorBuilder
                .itemTitle(editRequest.getItemTitle())
                .itemTime(editRequest.getItemTime())
                .category(editRequest.getCategory())
                .itemAddress(editRequest.getItemAddress())
                .budget(editRequest.getBudget())
                .isPrivate(editRequest.getIsPrivate())
                .itemContent(editRequest.getItemContent())
                .build();
        toDo.edit(toDoEditor);

        return ToDoResponse.builder()
                .dateId(dateId)
                .itemContent(toDo.getItemContent())
                .itemId(toDo.getId())
                .itemTime(toDo.getItemTime())
                .itemTitle(toDo.getItemTitle())
                .itemAddress(toDo.getItemAddress())
                .category(toDo.getCategory())
                .itemContent(toDo.getItemContent())
                .budget(toDo.getBudget())
                .build();
    }

    @Transactional
    public void delete(Long plannerId, Long dateId, Long toDoId) {

        // 플래너와 사용자에 대한 검증
        Planner planner = validatingService.validatePlannerAndUserAccess(plannerId);
        // 캘린더에 대한 검증
        Calendar calendar = validatingService.validateCalendarAccess(planner, dateId);
        // 투두에 대한 검증
        ToDo toDo = validatingService.validateToDoAccess(calendar, toDoId);

        // 투두에서 캘린더를 갖고 옴
        calendar = toDo.getCalendar();

        // 캘린더의 투두 리스트에서 투두 제거
        calendar.getScheduleItemList().remove(toDo);

        toDoRepository.delete(toDo);
    }


    @Transactional
    public List<CalendarResponse> getCalendarScheduleList(Long plannerId) {
        // 캘린더 서비스를 가져오면 순환참조 문제로 다시 작성
        List<Calendar> calendarList = calendarRepository.findByPlannerId(plannerId);
        ArrayList<CalendarResponse> calendarResponses = new ArrayList<>();

        for (Calendar calendar : calendarList) {
            CalendarResponse calendarResponse = CalendarResponse.builder()
                    .dateId(calendar.getId())
                    .dateTitle(calendar.getDateTitle())
                    .createAt(calendar.getCreatedAt())
                    .plannerId(calendar.getPlanner().getId())
                    .build();
            calendarResponses.add(calendarResponse);
        }

        // 투두를 조회하여 캘린더 리스트에 추가
        List<CalendarResponse> updatedCalendarResponses = new ArrayList<>();
        for (CalendarResponse calendarResponse : calendarResponses) {
            List<ToDoResponse> toDoResponses = getScheduleItemList(calendarResponse.getDateId());
            CalendarResponse updatedCalendarResponse = CalendarResponse.builder()
                    .dateId(calendarResponse.getDateId())
                    .dateTitle(calendarResponse.getDateTitle())
                    .createAt(calendarResponse.getCreateAt())
                    .plannerId(calendarResponse.getPlannerId())
                    .scheduleItemList(toDoResponses)
                    .build();
            updatedCalendarResponses.add(updatedCalendarResponse);
        }
        return updatedCalendarResponses;
    }
}

