package server.domain.planner.domain.Todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.planner.dto.request.todo.BudgetCreateRequest;
import server.domain.planner.dto.request.todo.BudgetUpdateRequest;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class BudgetTodo extends Todo {

    // 예산 타입
    @Enumerated(EnumType.STRING)
    private BudgetType budgetType;

    // 예산
    private Integer budget;


    // 예산 투두 생성
    public static BudgetTodo createBudgetTodo (BudgetCreateRequest request) {

        return BudgetTodo.builder()
                .todoType(TodoType.BUDGET)
                .todoTitle(request.getTodoTitle())
                .todoDate(request.getTodoDate())
                .todoContent(request.getTodoContent())
                .isPrivate(request.getIsPrivate())
                .budgetType(request.getBudgetType())
                .budget(request.getBudget())
                .build();
    }

    // 예산 투두 수정
    public void updateBudgetTodo (BudgetUpdateRequest request) {

        this.todoTitle = request.getTodoTitle();
        this.todoDate = request.getTodoDate();
        this.todoContent = request.getTodoContent();
        this.isPrivate = request.getIsPrivate();
        this.budgetType = request.getBudgetType();
        this.budget = request.getBudget();
    }
}
