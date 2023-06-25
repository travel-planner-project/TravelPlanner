package server.domain.planner.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.planner.domain.Date;
import server.domain.planner.domain.Todo.*;
import server.domain.planner.dto.request.todo.*;
import server.domain.planner.repository.DateRepository;
import server.domain.planner.repository.PlannerRepository;
import server.domain.planner.repository.todo.*;
import server.global.code.ErrorCode;
import server.global.exception.HandlableException;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class TodoService {

    private final PlannerRepository plannerRepository;
    private final DateRepository dateRepository;

    private final TodoRepository todoRepository;
    private final GeneralRepository generalRepository;
    private final AccommodationRepository accommodationRepository;
    private final AttractionRepository attractionRepository;
    private final BudgetRepository budgetRepository;
    private final TransportRepository transportRepository;


    // [START] CREATE ======================================================

    // 숙박 투두
    @Transactional
    public Todo createAccommodationTodo(
            AccommodationCreateRequest request, Long plannerId, Long dateId
    ) {
        Todo todo = AccommodationTodo.createAccommodationTodo(request);
        todoRepository.saveAndFlush(todo);

        Date date = dateRepository.findById(dateId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_DATE));
        date.createTodo(todo);

        dateRepository.saveAndFlush(date);

        return todo;
    }

    // 관광지 투두
    @Transactional
    public Todo createAttractionTodo (
            AttractionCreateRequest request, Long plannerId, Long dateId
    ) {
        Todo todo = AttractionTodo.createAttractionTodo(request);
        todoRepository.saveAndFlush(todo);

        Date date = dateRepository.findById(dateId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_DATE));
        date.createTodo(todo);

        todoRepository.saveAndFlush(todo);

        return todo;
    }

    // 일반 투두
    @Transactional
    public Todo createGeneralTodo (
            GeneralTodoCreateRequest request, Long plannerId, Long dateId
    ) {
        Todo todo = GeneralTodo.createGeneralTodo(request);
        todoRepository.saveAndFlush(todo);

        Date date = dateRepository.findById(dateId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_DATE));
        date.createTodo(todo);

        todoRepository.saveAndFlush(todo);

        return todo;
    }

    // 예산 투두
    @Transactional
    public Todo createBudgetTodo (
            BudgetCreateRequest request, Long plannerId, Long dateId
    ) {
        Todo todo = BudgetTodo.createBudgetTodo(request);
        todoRepository.saveAndFlush(todo);

        Date date = dateRepository.findById(dateId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_DATE));
        date.createTodo(todo);

        todoRepository.saveAndFlush(todo);

        return todo;
    }

    // 교통 투두
    @Transactional
    public Todo createTransportTodo (
            TransportCreateRequest request, Long plannerId, Long dateId
    ) {
        Todo todo = TransportTodo.createTransportTodo(request);
        todoRepository.saveAndFlush(todo);

        Date date = dateRepository.findById(dateId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_DATE));
        date.createTodo(todo);

        todoRepository.saveAndFlush(todo);

        return todo;
    }


    // [END] CREATE ======================================================


    // [START] UPDATE ======================================================


    // 숙박 투두
    @Transactional
    public AccommodationTodo updateAccommodationTodo (
            AccommodationUpdateRequest request
    ) {
        AccommodationTodo todo = accommodationRepository.findById(request.getTodoId())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_TODO));

        todo.updateAccommodationTodo(request);
        accommodationRepository.flush();

        return todo;
    }

    // 관광 투두
    @Transactional
    public AttractionTodo updateAttractionTodo (
            AttractionUpdateRequest request
    ) {
        AttractionTodo todo = attractionRepository.findById(request.getTodoId())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_TODO));

        todo.upddateAttractionTodo(request);
        attractionRepository.flush();

        return todo;
    }

    // 예산 투두
    @Transactional
    public BudgetTodo updateBudgetTodo (
            BudgetUpdateRequest request
    ) {
        BudgetTodo todo = budgetRepository.findById(request.getTodoId())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_TODO));

        todo.updateBudgetTodo(request);
        budgetRepository.flush();

        return todo;
    }

    // 일반 투두
    @Transactional
    public GeneralTodo updateTodo (
            GeneralTodoUpdateRequest request
    ) {
        GeneralTodo todo = generalRepository.findById(request.getTodoId())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_TODO));

        todo.updateGeneralTodo(request);
        generalRepository.flush();

        return todo;
    }

    // 교통 투두
    @Transactional
    public TransportTodo updateTransportTodo (
            TransportUpdateRequest request
    ) {
        TransportTodo todo = transportRepository.findById(request.getTodoId())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_TODO));

        todo.updateTransportTodo(request);
        transportRepository.flush();

        return todo;
    }


    // [END] UPDATE ======================================================


    // [START] DELETE ======================================================


    // 관광 투두
    @Transactional
    public void deleteAttractionTodo (
            TodoDeleteRequest request
    ) {
        Todo todo = attractionRepository.findById(request.getTodoId())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_TODO));

        Date date = dateRepository.findByTodosTodoId(request.getTodoId());
        date.deleteTodo(todo);

        attractionRepository.delete((AttractionTodo) todo);
    }

    // 숙박 투두
    @Transactional
    public void deleteAccommodationTodo (
            TodoDeleteRequest request
    ) {
        Todo todo = accommodationRepository.findById(request.getTodoId())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_TODO));

        Date date = dateRepository.findByTodosTodoId(request.getTodoId());
        date.deleteTodo(todo);

        accommodationRepository.delete((AccommodationTodo) todo);
    }

    // 예산 투두
    @Transactional
    public void deleteBudgetTodo (
            TodoDeleteRequest request
    ) {
        Todo todo = budgetRepository.findById(request.getTodoId())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_TODO));

        Date date = dateRepository.findByTodosTodoId(request.getTodoId());
        date.deleteTodo(todo);

        budgetRepository.delete((BudgetTodo) todo);
    }

    // 일반 투두
    @Transactional
    public void deleteTodo (
            TodoDeleteRequest request
    ) {
        Todo todo = generalRepository.findById(request.getTodoId())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_TODO));

        Date date = dateRepository.findByTodosTodoId(request.getTodoId());
        date.deleteTodo(todo);

        generalRepository.delete((GeneralTodo) todo);
    }

    // 교통 투두
    @Transactional
    public void deleteTransportTodo (
            TodoDeleteRequest request
    ){
        Todo todo = transportRepository.findById(request.getTodoId())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_PLANNER));

        Date date = dateRepository.findByTodosTodoId(request.getTodoId());
        date.deleteTodo(todo);

        transportRepository.delete((TransportTodo) todo);
    }


    // [END] DELETE ======================================================


}
