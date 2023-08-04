package travelplanner.project.demo.planner.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Calendar {

    @Id
    @GeneratedValue
    private Long id;

    private String eachDate;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "calendar")
    @Builder.Default
    private List<ToDo> toDoList = new ArrayList<>();

    public void mappingToDo(ToDo toDo) {
        toDoList.add(toDo);
    }

    // 플래너 연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id")
    private Planner planner;

    public void mappingPlanner(Planner planner) {
        this.planner = planner;
        planner.mappingCalendar(this);
    }

}
