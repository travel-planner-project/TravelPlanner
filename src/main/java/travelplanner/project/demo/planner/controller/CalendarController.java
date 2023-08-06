package travelplanner.project.demo.planner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import travelplanner.project.demo.planner.dto.request.CalendarCreateRequest;
import travelplanner.project.demo.planner.dto.request.CalendarEditRequest;
import travelplanner.project.demo.planner.dto.response.CalendarResponse;
import travelplanner.project.demo.planner.service.CalendarService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/pub/create-date/{plannerId}")
    public void createDate(@DestinationVariable Long plannerId, CalendarCreateRequest request) {
        calendarService.createDate(plannerId, request);
        List<CalendarResponse> calendarList = calendarService.getCalendarList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId, calendarList);
    }

    @MessageMapping("/pub/update-date/{plannerId}")
    public void updateDate(@DestinationVariable Long plannerId, CalendarEditRequest request) {
        calendarService.updateDate(plannerId, request);
        List<CalendarResponse> calendarList = calendarService.getCalendarList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                calendarList);

    }

    @MessageMapping("/pub/delete-date/{plannerId}")
    public void deleteDate(@DestinationVariable Long plannerId, Long dateId) {
        calendarService.deleteDate(plannerId);
        List<CalendarResponse> calendarList = calendarService.getCalendarList();
        simpMessagingTemplate.convertAndSend("/sub/planner-message/" + plannerId,
                calendarList);
    }

}
