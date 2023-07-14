package server.domain.planner.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.domain.planner.domain.Todo.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TodoResponse {

    @ApiModelProperty(example = "1")
    private Long todoId;

    @ApiModelProperty(example = "ACCOMMODATION")
    private TodoType todoType;

    @ApiModelProperty(example = "false")
    private Boolean isPrivate;

    @ApiModelProperty(example = "투두 제목")
    private String todoTitle;

    private LocalDateTime todoDate;

    @ApiModelProperty(example = "투두 내용")
    private String todoContent;


    // 투두 타입 별 특이 내용
    @ApiModelProperty(example = "공지")
    private String tag;

    @ApiModelProperty(example = "숙박 주소")
    private String accommodationAddress;

    @ApiModelProperty(example = "관광지 주소")
    private String attractionAddress;

    @ApiModelProperty(example = "SHOPPING")
    private BudgetType budgetType;

    @ApiModelProperty(example = "10000")
    private Integer budget;

    @ApiModelProperty(example = "TRAIN")
    private TransportType transportType;

    @ApiModelProperty(example = "1시")
    private String departTIme;

    @ApiModelProperty(example = "2시")
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

    // 일반투두
    private TodoResponse (GeneralTodo entity) {

        this.todoId = entity.getTodoId();
        this.todoType = entity.getTodoType();
        this.isPrivate = entity.getIsPrivate();
        this.todoTitle = entity.getTodoTitle();
        this.todoDate = entity.getTodoDate();
        this.todoContent = entity.getTodoContent();
        this.tag = entity.getTag();
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

        } else if (todo instanceof GeneralTodo) {
            return new TodoResponse((GeneralTodo) todo);
        }

        return null;
    }

    // response 리스트 생성
    public static List<TodoResponse> todoResponses (List<Todo> entityList) {

        return entityList.stream().map(TodoResponse::createTodoResponse).collect(Collectors.toList());
    }
}
