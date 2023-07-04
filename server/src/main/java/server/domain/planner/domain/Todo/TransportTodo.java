package server.domain.planner.domain.Todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.planner.dto.request.todo.TransportCreateRequest;
import server.domain.planner.dto.request.todo.TransportUpdateRequest;

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
public class TransportTodo extends Todo {

    // 출발 시간
    private String departTIme;

    // 도착 시간
    private String arriveTime;

    // 교통 유형
    @Enumerated(EnumType.STRING)
    private TransportType transportType;


    // 교통 투두 생성
    public static TransportTodo createTransportTodo (TransportCreateRequest request) {

        return TransportTodo.builder()
                .todoType(TodoType.TRANSPORT)
                .todoTitle(request.getTodoTitle())
                .todoDate(request.getTodoDate())
                .todoContent(request.getTodoContent())
                .isPrivate(request.getIsPrivate())
                .departTIme(request.getDepartTime())
                .arriveTime(request.getArriveTime())
                .build();
    }

    // 교통 투두 수정
    public void updateTransportTodo (TransportUpdateRequest request) {

        this.todoTitle = request.getTodoTitle();
        this.todoDate = request.getTodoDate();
        this.todoContent = request.getTodoContent();
        this.isPrivate = request.getIsPrivate();
        this.departTIme = request.getDepartTime();
        this.arriveTime = request.getArriveTime();
    }
}
