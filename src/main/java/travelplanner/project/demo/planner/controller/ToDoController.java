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

    private final TokenUtil tokenUtil;
    private final ToDoService toDoService;
    private final CalendarService calendarService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/create-todo/{plannerId}/{dateId}")
    public void create(@DestinationVariable Long plannerId,
                       @DestinationVariable Long dateId,
                       ToDoCraeteRequest request,
                       @Header("Authorization") String accessToken) {

        tokenUtil.getAuthenticationFromToken(accessToken);
        //        List<ToDoResponse> scheduleItemList = toDoService.getScheduleItemList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","add-schedule", "msg", toDoService.createTodo(accessToken, plannerId, dateId, request) // 사용자가 입력한 todo

                )
        );
    }

    @MessageMapping("/update-todo/{plannerId}/{dateId}/{toDoId}")
    public void edit(@DestinationVariable Long plannerId,
                     @DestinationVariable Long dateId,
                     @DestinationVariable Long toDoId,
                     ToDoEditRequest request,
                     @Header("Authorization") String accessToken) {

        tokenUtil.getAuthenticationFromToken(accessToken);
        //        List<ToDoResponse> scheduleItemList = toDoService.getScheduleItemList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","modify-schedule", "msg", toDoService.editTodo(accessToken, plannerId, dateId, toDoId, request)
                )
        );
    }

    @MessageMapping("/delete-todo/{plannerId}/{dateId}/{toDoId}")
    public void delete(@DestinationVariable Long plannerId,
                       @DestinationVariable Long dateId,
                       @DestinationVariable Long toDoId,
                       @Header("Authorization") String accessToken) {

        tokenUtil.getAuthenticationFromToken(accessToken);
        toDoService.delete(accessToken, plannerId, dateId, toDoId);
        List<CalendarResponse> calendarScheduleList = toDoService.getCalendarScheduleList(plannerId);
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","delete-schedule", "msg", calendarScheduleList
                )
        );
    }
}