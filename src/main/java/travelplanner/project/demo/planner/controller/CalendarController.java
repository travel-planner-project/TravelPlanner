package travelplanner.project.demo.planner.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.planner.dto.request.CalendarCreateRequest;
import travelplanner.project.demo.planner.dto.request.CalendarEditRequest;
import travelplanner.project.demo.planner.dto.response.CalendarResponse;
import travelplanner.project.demo.planner.service.CalendarService;
import travelplanner.project.demo.planner.service.ToDoService;
import travelplanner.project.demo.planner.service.ValidatingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CalendarController {

    private final TokenUtil tokenUtil;
    private final ToDoService toDoService;
    private final CalendarService calendarService;
    private final ValidatingService validatingService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/create-date/{plannerId}")
    public void createDate(@DestinationVariable Long plannerId, @Header("Authorization") String authorization, CalendarCreateRequest request) {

        tokenUtil.getJWTTokenFromWebSocket(authorization);

//        List<CalendarResponse> calendarList = calendarService.getCalendarList();
//
//        log.info("리스폰스: " + calendarList.toString());
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","add-date", "msg", calendarService.createDate(plannerId, request)
                )
        );
    }

    @MessageMapping("/update-date/{plannerId}/{dateId}")
    public void updateDate(@DestinationVariable Long plannerId,
                           @DestinationVariable Long dateId,
                           CalendarEditRequest request,
                           @Header("Authorization") String authorization) {

        tokenUtil.getJWTTokenFromWebSocket(authorization);
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","modify-date", "msg", calendarService.updateDate(plannerId, dateId, request)
                )
        );
    }

    @MessageMapping("/delete-date/{plannerId}/{dateId}")
    public void deleteDate(@DestinationVariable Long plannerId,
                           @DestinationVariable Long dateId,
                           @Header("Authorization") String authorization) {

        tokenUtil.getJWTTokenFromWebSocket(authorization);
        calendarService.deleteDate(plannerId, dateId);
//        기존 로직
//        List<CalendarResponse> calendarList = calendarService.getCalendarList();
//        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId);
        List<CalendarResponse> calendarScheduleList = toDoService.getCalendarScheduleList(plannerId);
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","delete-schedule", "msg", calendarScheduleList
                )
        );
    }



}
