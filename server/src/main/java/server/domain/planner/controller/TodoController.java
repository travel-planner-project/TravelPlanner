package server.domain.planner.controller;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import server.domain.planner.dto.request.todo.*;
import server.domain.planner.service.TodoService;

import java.util.Map;

@RestController
@AllArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final SimpMessagingTemplate simpMessagingTemplate;


    // [START] CREATE ======================================================


    // 숙박 투두
    @MessageMapping("create-accommodation/{plannerId}/{dateId}")
    public void createAccommodationTodo (
            @DestinationVariable("plannerId") Long plannerId,
            @DestinationVariable("dateId") Long dateId,
            AccommodationCreateRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of(
                        "type", "create-accommodation",
                        "msg", todoService.createAccommodationTodo(request, plannerId, dateId),
                        "dateId", dateId
                )
        );
    }

    // 관광 투두
    @MessageMapping("create-attraction/{plannerId}/{dateId}")
    public void createAttractionTodo (
            @DestinationVariable("plannerId") Long plannerId,
            @DestinationVariable("dateId") Long dateId,
            AttractionCreateRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of(
                        "type", "create-attraction",
                        "msg", todoService.createAttractionTodo(request, plannerId, dateId),
                        "dateId", dateId
                )
        );
    }

    // 교통 투두
    @MessageMapping("create-transport/{plannerId}/{dateId}")
    public void createTransportTodo (
            @DestinationVariable("plannerId") Long plannerId,
            @DestinationVariable("dateId") Long dateId,
            TransportCreateRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of(
                        "type", "create-transport",
                        "msg", todoService.createTransportTodo(request, plannerId, dateId),
                        "dateId", dateId
                )
        );
    }

    // 일반 투두
    @MessageMapping("create-general/{plannerId}/{dateId}")
    public void createGeneralTodo (
            @DestinationVariable("plannerId") Long plannerId,
            @DestinationVariable("dateId") Long dateId,
            GeneralTodoCreateRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of(
                        "type", "create-general",
                        "msg", todoService.createGeneralTodo(request, plannerId, dateId),
                        "dateId", dateId
                )
        );
    }

    // 예산 투두
    @MessageMapping("create-budget/{plannerId}/{dateId}")
    public void createBudgetTodo (
            @DestinationVariable("plannerId") Long plannerId,
            @DestinationVariable("dateId") Long dateId,
            BudgetCreateRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of(
                        "type", "create-budget",
                        "msg", todoService.createBudgetTodo(request, plannerId, dateId),
                        "dateId", dateId
                )
        );
    }


    // [END] CREATE ======================================================


    // [START] UPDATE ======================================================


    // 숙박 투두
    @MessageMapping("update-accommodation/{plannerId}")
    public void updateAccommodationTodo(
            @DestinationVariable("plannerId") Long plannerId
            , AccommodationUpdateRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of (
                        "type", "update-accommodation",
                        "msg", todoService.updateAccommodationTodo(request)
                )
        );
    }

    // 관광 투두
    @MessageMapping("update-attraction/{plannerId}")
    public void updateAttractionTodo(
            @DestinationVariable("plannerId") Long plannerId
            , AttractionUpdateRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of (
                        "type", "update-attraction",
                        "msg", todoService.updateAttractionTodo(request)
                )
        );
    }

    // 예산 투두
    @MessageMapping("update-budget/{plannerId}")
    public void updateBudgetTodo(
            @DestinationVariable("plannerId") Long plannerId
            , BudgetUpdateRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of (
                        "type", "update-budget",
                        "msg", todoService.updateBudgetTodo(request)
                )
        );
    }

    // 일반 투두
    @MessageMapping("update-general/{plannerId}")
    public void updateTodo(
            @DestinationVariable("plannerId") Long plannerId
            , GeneralTodoUpdateRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of (
                        "type", "update-accommodation",
                        "msg", todoService.updateTodo(request)
                )
        );
    }

    // 교통 투두
    @MessageMapping("update-transport/{plannerId}")
    public void updateTransportTodo(
            @DestinationVariable("plannerId") Long plannerId
            , TransportUpdateRequest request
    ) throws Exception {

        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of (
                        "type", "update-transport",
                        "msg", todoService.updateTransportTodo(request)
                )
        );
    }


    // [END] UPDATE ======================================================


    // [START] DELETE ======================================================


    // 숙박 투두
    @MessageMapping("delete-accommodation/{plannerId}")
    public void deleteAccommodationTodo(
            @DestinationVariable("plannerId") Long plannerId
            , TodoDeleteRequest request
    ) {
        todoService.deleteAccommodationTodo(request);
        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of(
                        "type", "delete-accommodation",
                        "msg", request.getTodoId()
                )
        );
    }

    // 관광 투두
    @MessageMapping("delete-attraction/{plannerId}")
    public void deleteAttractionTodo(
            @DestinationVariable("plannerId") Long plannerId
            , TodoDeleteRequest request
    ) {
        todoService.deleteAttractionTodo(request);
        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of(
                        "type", "delete-attraction",
                        "msg", request.getTodoId()
                )
        );
    }

    // 예산 투두
    @MessageMapping("delete-budget/{plannerId}")
    public void deleteBudgetTodo(
            @DestinationVariable("plannerId") Long plannerId
            , TodoDeleteRequest request
    ) {
        todoService.deleteBudgetTodo(request);
        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of(
                        "type", "delete-budget",
                        "msg", request.getTodoId()
                )
        );
    }

    // 일반 투두
    @MessageMapping("delete-general/{plannerId}")
    public void deleteTodo(
            @DestinationVariable("plannerId") Long plannerId
            , TodoDeleteRequest request
    ) {
        todoService.deleteTodo(request);
        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of(
                        "type", "delete-todo",
                        "msg", request.getTodoId()
                )
        );
    }

    // 교통 투두
    @MessageMapping("delete-transport/{plannerId}")
    public void deleteTransportTodo(
            @DestinationVariable("plannerId") Long plannerId
            , TodoDeleteRequest request
    ) {
        todoService.deleteTransportTodo(request);
        simpMessagingTemplate.convertAndSend(
                "/sub/planner-message/" + plannerId
                , Map.of(
                        "type", "delete-transport",
                        "msg", request.getTodoId()
                )
        );
    }


    // [END] DELETE ======================================================


}
