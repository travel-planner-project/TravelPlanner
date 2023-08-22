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
import travelplanner.project.demo.planner.dto.response.CalendarResponse;
import travelplanner.project.demo.planner.dto.response.ToDoResponse;
import travelplanner.project.demo.planner.service.CalendarService;
import travelplanner.project.demo.planner.service.ToDoService;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;
    private final CalendarService calendarService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final TokenUtil tokenUtil;

    @MessageMapping("/create-todo/{plannerId}/{dateId}")
    public void create(@DestinationVariable Long plannerId,
                       @DestinationVariable Long dateId,
                       ToDoCraeteRequest request,
                       @Header("Authorization") String athorization) {

        tokenUtil.getJWTTokenFromWebSocket(athorization);
        //        List<ToDoResponse> scheduleItemList = toDoService.getScheduleItemList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","add-schedule", "msg", toDoService.createTodo(plannerId, dateId, request) // 사용자가 입력한 todo

                )
        );
    }

    @MessageMapping("/update-todo/{plannerId}/{dateId}/{toDoId}")
    public void edit(@DestinationVariable Long plannerId,
                     @DestinationVariable Long dateId,
                     @DestinationVariable Long toDoId,
                     ToDoEditRequest request,
                     @Header("Authorization") String athorization) {

        tokenUtil.getJWTTokenFromWebSocket(athorization);
        //        List<ToDoResponse> scheduleItemList = toDoService.getScheduleItemList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","modify-schedule", "msg", toDoService.editTodo(plannerId, dateId, toDoId, request)
                )
        );
    }

    @MessageMapping("/delete-todo/{plannerId}/{dateId}/{toDoId}")
    public void delete(@DestinationVariable Long plannerId,
                       @DestinationVariable Long dateId,
                       @DestinationVariable Long toDoId,
                       @Header("Authorization") String athorization) {

        tokenUtil.getJWTTokenFromWebSocket(athorization);
        toDoService.delete(plannerId, dateId, toDoId);
//        List<ToDoResponse> scheduleItemList = toDoService.getScheduleItemList();
        List<CalendarResponse> calendarResponses = calendarService.getCalendarList(plannerId);
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","delete-schedule", "msg", calendarResponses
                )
        );
    }
}