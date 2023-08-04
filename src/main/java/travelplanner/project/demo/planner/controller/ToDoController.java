package travelplanner.project.demo.planner.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import travelplanner.project.demo.planner.dto.request.ToDoCraeteRequest;
import travelplanner.project.demo.planner.dto.request.ToDoEditRequest;
import travelplanner.project.demo.planner.dto.response.ToDoResponse;
import travelplanner.project.demo.planner.service.ToDoService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/pub/create-todo/{plannerId}/{dateId}")
    public void create(@DestinationVariable Long plannerId, ToDoCraeteRequest request
//            , @DestinationVariable Long dateId 이것 주석으로 해도 될지,..?
    ) {
        toDoService.addTodo(request);   // 시영지기 ㅇ;ㅂ략힌 toDo
        List<ToDoResponse> toDoList = toDoService.getToDoList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId, toDoList);
    }

    @MessageMapping("/pub/update-todo/{plannerId}")
    public void edit(@DestinationVariable Long plannerId, ToDoEditRequest request) {
        toDoService.editTodo(plannerId, request);
        List<ToDoResponse> toDoList = toDoService.getToDoList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId, toDoList);
    }

    @MessageMapping("/pub/delete-todo/{plannerId}")
    public void delete(@DestinationVariable Long plannerId) {
        toDoService.delete(plannerId);
        List<ToDoResponse> toDoList = toDoService.getToDoList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId, toDoList);
    }
}