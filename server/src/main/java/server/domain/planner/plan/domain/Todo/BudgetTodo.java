package server.domain.planner.plan.domain.Todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BudgetTodo extends Todo {

    // 예산 타입
    @Enumerated(EnumType.STRING)
    private BudgetType budgetType;

    // 예산
    private Integer budget;
}
