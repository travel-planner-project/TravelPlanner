package server.domain.planner.domain.Todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.planner.dto.request.todo.AccommodationCreateRequest;
import server.domain.planner.dto.request.todo.AccommodationUpdateRequest;

import javax.persistence.Entity;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class AccommodationTodo extends Todo {

    // 여행 주소
    private String accommodationAddress;


    // 여행 투두 추가
    public static AccommodationTodo createAccommodationTodo (AccommodationCreateRequest request) {

        return AccommodationTodo.builder()
                .todoType(TodoType.ACCOMMODATION)
                .todoTitle(request.getTodoTitle())
                .todoDate(request.getTodoDate())
                .todoContent(request.getTodoContent())
                .isPrivate(request.getIsPrivate())
                .accommodationAddress(request.getAccommodationAddress())
                .build();
    }

    // 여행 투두 수정
    public void updateAccommodationTodo (AccommodationUpdateRequest request) {

        this.todoTitle = request.getTodoTitle();
        this.todoDate = request.getTodoDate();
        this.todoContent = request.getTodoContent();
        this.isPrivate = request.getIsPrivate();
        this.accommodationAddress = request.getAccommodationAddress();
    }
}
