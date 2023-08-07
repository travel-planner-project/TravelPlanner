package travelplanner.project.demo.planner.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import travelplanner.project.demo.global.util.TokenUtil;
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
    private final TokenUtil tokenUtil;

    @MessageMapping("/create-todo/{plannerId}/{dateId}")
    public void create(@DestinationVariable Long plannerId,
                       @DestinationVariable Long dateId,
                       ToDoCraeteRequest request,
                       @Header("Authorization") String athorization) {

        tokenUtil.getJWTTokenFromWebSocket(athorization);
        toDoService.createTodo(plannerId, dateId, request);   // 시영지기 ㅇ;ㅂ략힌 toDo
        List<ToDoResponse> toDoList = toDoService.getToDoList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId, toDoList);
    }

    @MessageMapping("/update-todo/{plannerId}/{dateId}/{toDoId}")
    public void edit(@DestinationVariable Long plannerId,
                     @DestinationVariable Long dateId,
                     @DestinationVariable Long toDoId,
                     ToDoEditRequest request,
                     @Header("Authorization") String athorization) {

        tokenUtil.getJWTTokenFromWebSocket(athorization);
        toDoService.editTodo(plannerId, dateId, toDoId, request);
        List<ToDoResponse> toDoList = toDoService.getToDoList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId, toDoList);
    }

    @MessageMapping("/delete-todo/{plannerId}/{dateId}/{todoId}")
    public void delete(@DestinationVariable Long plannerId,
                       @DestinationVariable Long dateId,
                       @DestinationVariable Long toDoId,
                       @Header("Authorization") String athorization) {

        tokenUtil.getJWTTokenFromWebSocket(athorization);
        toDoService.delete(plannerId, dateId, toDoId);
        List<ToDoResponse> toDoList = toDoService.getToDoList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId, toDoList);
    }
}