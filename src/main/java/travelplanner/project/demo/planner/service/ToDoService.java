package travelplanner.project.demo.planner.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.planner.domain.*;
import travelplanner.project.demo.planner.dto.request.ToDoCraeteRequest;
import travelplanner.project.demo.planner.dto.request.ToDoEditRequest;
import travelplanner.project.demo.planner.dto.response.ToDoResponse;
import travelplanner.project.demo.planner.repository.GroupMemberRepository;
import travelplanner.project.demo.planner.repository.ToDoRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final ValidatingService validatingService;

    public List<ToDoResponse> getToDoList() {
        List<ToDo> ToDoList = toDoRepository.findAll();
        ArrayList<ToDoResponse> toDoResponses = new ArrayList<>();
        for (ToDo toDo : ToDoList){
            ToDoResponse toDoResponse = ToDoResponse.builder()
                    .dateId(toDo.getId())
                    .itemId(toDo.getCalendar().getId())
                    .itemTitle(toDo.getItemTitle())
                    .category(toDo.getCategory())
                    .itemDate(toDo.getItemDate())
                    .itemContent(toDo.getContent())
                    .isPrivate(toDo.getIsPrivate())
                    .budget(toDo.getBudget())
                    .itemAddress(toDo.getItemAddress())
                    .build();
            toDoResponses.add(toDoResponse);
        }
        return toDoResponses;
    }

    // 플래너 서비스에서 특정 플래너에 포함된 캘린더 및 투두를 갖고오기 위해 오버로딩
    public List<ToDoResponse> getToDoList(Long calendarId) {

        List<ToDo> toDoList = toDoRepository.findByCalendarId(calendarId);
        ArrayList<ToDoResponse> toDoResponses = new ArrayList<>();
        for (ToDo toDo : toDoList) {
            ToDoResponse toDoResponse = ToDoResponse.builder()
                    .dateId(toDo.getId())
                    .itemId(toDo.getCalendar().getId())
                    .category(toDo.getCategory())
                    .itemDate(toDo.getItemDate())
                    .itemContent(toDo.getContent())
                    .isPrivate(toDo.getIsPrivate())
                    .budget(toDo.getBudget())
                    .itemAddress(toDo.getItemAddress())
                    .build();
            toDoResponses.add(toDoResponse);
        }
        return toDoResponses;
    }



    public void createTodo(Long plannerId, Long dateId,
                           ToDoCraeteRequest request) {
        // 플래너와 사용자에 대한 검증
        Planner planner = validatingService.validatePlannerAndUserAccess(plannerId);
        // 캘린더에 대한 검증
        Calendar calendar = validatingService.validateCalendarAccess(planner, dateId);

        ToDo todo = ToDo.builder()
                .calendar(calendar)
                .itemTitle(request.getItemTitle())
                .itemDate(request.getItemDate())
                .category(request.getCategory())
                .content(request.getItemContent())
                .isPrivate(request.getIsPrivate())
                .budget(request.getBudget())
                .itemAddress(request.getItemAddress())
                .build();
        toDoRepository.save(todo);
    }

    @Transactional
    public void editTodo(
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
                .itemDate(editRequest.getItemDate())
                .category(editRequest.getCategory())
                .itemAddress(editRequest.getItemAddress())
                .budget(editRequest.getBudget())
                .isPrivate(editRequest.getIsPrivate())
                .content(editRequest.getContent())
                .build();
        toDo.edit(toDoEditor);
    }

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
        calendar.getToDoList().remove(toDo);

        toDoRepository.delete(toDo);
    }


}

