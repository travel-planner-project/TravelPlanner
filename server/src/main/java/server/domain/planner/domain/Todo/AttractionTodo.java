package server.domain.planner.domain.Todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.planner.dto.request.todo.AttractionCreateRequest;
import server.domain.planner.dto.request.todo.AttractionUpdateRequest;

import javax.persistence.Entity;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class AttractionTodo extends Todo {

    // 관광지 주소
    private String attractionAddress;


    // 관광지 투두 추가
    public static AttractionTodo createAttractionTodo (AttractionCreateRequest request) {

        return AttractionTodo.builder()
                .todoType(TodoType.ATTRACTIONS)
                .todoTitle(request.getTodoTitle())
                .todoDate(request.getTodoDate())
                .todoContent(request.getTodoContent())
                .isPrivate(request.getIsPrivate())
                .attractionAddress(request.getAttractionAddress())
                .build();
    }

    // 관광지 투두 수정
    public void upddateAttractionTodo (AttractionUpdateRequest request) {

        this.todoTitle = request.getTodoTitle();
        this.todoDate = request.getTodoDate();
        this.todoContent = request.getTodoContent();
        this.isPrivate = request.getIsPrivate();
        this.attractionAddress = request.getAttractionAddress();
    }
}
