package server.domain.planner.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import server.domain.planner.domain.Todo.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TodoResponse {

    // 투두 인덱스
    private Long todoId;

    // 투두 타입
    private TodoType todoType;

    // 투두 공개 여부
    private Boolean isPrivate;

    // 투두 제목
    private String todoTitle;

    // 투두 실행 날짜
    private LocalDateTime todoDate;

    // 투두 내용
    private String todoContent;


    // 투두 타입 별 특이 내용
    // 숙소 주소
    private String accommodationAddress;

    // 관광지 주소
    private String attractionAddress;

    // 예산 타입
    private BudgetType budgetType;

    // 예산
    private Integer budget;

    // 교통 유형
    private TransportType transportType;

    // 출발 시간과 도착 시간
    private String departTIme;
    private String arriveTime;




    // 숙박 투두
    private TodoResponse(AccommodationTodo entity) {

        this.todoId = entity.getTodoId();
        this.todoType = entity.getTodoType();
        this.isPrivate = entity.getIsPrivate();
        this.todoTitle = entity.getTodoTitle();
        this.todoDate = entity.getTodoDate();
        this.todoContent = entity.getTodoContent();
        this.accommodationAddress = entity.getAccommodationAddress();
    }

    // 관광 투두
    private TodoResponse (AttractionTodo entity) {

        this.todoId = entity.getTodoId();
        this.todoType = entity.getTodoType();
        this.isPrivate = entity.getIsPrivate();
        this.todoTitle = entity.getTodoTitle();
        this.todoDate = entity.getTodoDate();
        this.todoContent = entity.getTodoContent();
        this.attractionAddress = entity.getAttractionAddress();
    }

    // 예산 투두
    private TodoResponse (BudgetTodo entity) {

        this.todoId = entity.getTodoId();
        this.todoType = entity.getTodoType();
        this.isPrivate = entity.getIsPrivate();
        this.todoTitle = entity.getTodoTitle();
        this.todoDate = entity.getTodoDate();
        this.todoContent = entity.getTodoContent();
        this.budgetType = entity.getBudgetType();
        this.budget = entity.getBudget();
    }

    // 교통 투두
    private TodoResponse (TransportTodo entity) {

        this.todoId = entity.getTodoId();
        this.todoType = entity.getTodoType();
        this.isPrivate = entity.getIsPrivate();
        this.todoTitle = entity.getTodoTitle();
        this.todoDate = entity.getTodoDate();
        this.todoContent = entity.getTodoContent();
        this.transportType = entity.getTransportType();
        this.departTIme = entity.getDepartTIme();
        this.arriveTime = entity.getArriveTime();
    }



    public static TodoResponse createTodoResponse (Todo todo) {

        if (todo instanceof AccommodationTodo) {
            return new TodoResponse((AccommodationTodo) todo);

        } else if (todo instanceof AttractionTodo) {
            return new TodoResponse((AttractionTodo) todo);

        } else if (todo instanceof BudgetTodo) {
            return new TodoResponse((BudgetTodo) todo);

        } else if (todo instanceof TransportTodo) {
            return new TodoResponse((TransportTodo) todo);
        }

        return null;
    }

    // response 리스트 생성
    public static List<TodoResponse> todoResponses (List<Todo> entityList) {

        return entityList.stream().map(TodoResponse::createTodoResponse).collect(Collectors.toList());
    }
}
