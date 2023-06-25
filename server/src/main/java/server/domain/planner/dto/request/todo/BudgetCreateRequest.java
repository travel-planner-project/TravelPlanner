package server.domain.planner.dto.request.todo;

import lombok.Data;
import lombok.NoArgsConstructor;
import server.domain.planner.domain.Todo.BudgetType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BudgetCreateRequest {

    private String todoTitle;
    private LocalDateTime todoDate;
    private String todoContent;
    private Boolean isPrivate;
    private BudgetType budgetType;
    private Integer budget;
}
