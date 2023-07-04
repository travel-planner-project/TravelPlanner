package server.domain.planner.domain.Todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.planner.dto.request.todo.GeneralTodoCreateRequest;
import server.domain.planner.dto.request.todo.GeneralTodoUpdateRequest;

import javax.persistence.Entity;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class GeneralTodo extends Todo {

    // 테스트 겸 가상으로 넣어봤습니다.
    private String tag;

    // 투두 생성
    public static GeneralTodo createGeneralTodo (GeneralTodoCreateRequest request) {

        return GeneralTodo.builder()
                .todoType(TodoType.GENERAL)
                .todoTitle(request.getTodoTitle())
                .todoDate(request.getTodoDate())
                .todoContent(request.getTodoContent())
                .isPrivate(request.getIsPrivate())
                .tag(request.getTag())
                .build();
    }

    // 투두 수정
    public void updateGeneralTodo (GeneralTodoUpdateRequest request) {

        this.todoTitle = request.getTodoTitle();
        this.todoDate = request.getTodoDate();
        this.todoContent = request.getTodoContent();
        this.isPrivate = request.getIsPrivate();
    }
}

