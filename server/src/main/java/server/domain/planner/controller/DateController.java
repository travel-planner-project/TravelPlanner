package server.domain.planner.controller;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import server.domain.planner.dto.request.DateCreateRequest;
import server.domain.planner.dto.request.DateDeleteRequest;
import server.domain.planner.dto.request.DateUpdateRequest;
import server.domain.planner.service.DateService;

import java.util.Map;


@AllArgsConstructor
@RestController
public class DateController {

    private final DateService dateService;
    private final SimpMessagingTemplate simpMessagingTemplate;


    // 날짜 추가
    @MessageMapping(value = "create-date/{plannerId}")
    public void createDate (
            @DestinationVariable("plannerId") Long plannerId
            , DateCreateRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend (
                "/sub/planner-message/" + plannerId
                , Map.of (
                "type", "create-date",
                "msg", dateService.createDate(request, plannerId)
                )
        );
    }

    // 날짜 수정
    @MessageMapping(value = "update-date/{plannerId}")
    public void updateDate (
            @DestinationVariable("plannerId") Long plannerId
            , DateUpdateRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of (
                        "type", "update-date",
                        "msg", dateService.updateDate(request)
                )
        );
    }

    // 날짜 삭제
    @MessageMapping(value = "delete-date/{plannerId}")
    public void deleteDate (
            @DestinationVariable("plannerId") Long plannerId
            , DateDeleteRequest request
    ) throws Exception {

        dateService.deleteDate(request.getDateId(), plannerId);

        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of(
                        "type", "delete-date",
                        "msg", request.getDateId()
                )
        );
    }
}
