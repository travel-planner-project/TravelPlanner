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
import travelplanner.project.demo.planner.service.ValidatingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ValidatingService validatingService;
    private final TokenUtil tokenUtil;

    @MessageMapping("/create-date/{plannerId}")
    public void createDate(@DestinationVariable Long plannerId, @Header("Authorization") String athorization, CalendarCreateRequest request) {

        tokenUtil.getJWTTokenFromWebSocket(athorization);

        calendarService.createDate(plannerId, request);
        List<CalendarResponse> calendarList = calendarService.getCalendarList();

        log.info("리스폰스: " + calendarList.toString());
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","add-date", "msg", calendarList
                )
        );
    }

    @MessageMapping("/update-date/{plannerId}/{dateId}")
    public void updateDate(@DestinationVariable Long plannerId,
                           @DestinationVariable Long dateId,
                           CalendarEditRequest request,
                           @Header("Authorization") String athorization) {

        tokenUtil.getJWTTokenFromWebSocket(athorization);
        calendarService.updateDate(plannerId, dateId, request);
        List<CalendarResponse> calendarList = calendarService.getCalendarList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","modify-date", "msg", calendarList
                )
        );
    }

    @MessageMapping("/delete-date/{plannerId}/{dateId}")
    public void deleteDate(@DestinationVariable Long plannerId,
                           @DestinationVariable Long dateId,
                           @Header("Authorization") String athorization) {

        tokenUtil.getJWTTokenFromWebSocket(athorization);
        calendarService.deleteDate(plannerId, dateId);
        List<CalendarResponse> calendarList = calendarService.getCalendarList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                Map.of("type","delete-date", "msg", calendarList
                )
        );
    }

}
