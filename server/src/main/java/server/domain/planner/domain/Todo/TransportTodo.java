package server.domain.planner.domain.Todo;

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
public class TransportTodo extends Todo {

    // 출발 시간
    private String departTIme;

    // 도착 시간
    private String arriveTime;

    // 교통 유형
    @Enumerated(EnumType.STRING)
    private TransportType transportType;
}
