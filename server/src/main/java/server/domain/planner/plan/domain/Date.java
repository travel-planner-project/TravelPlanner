package server.domain.planner.plan.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.planner.plan.domain.Todo.*;

import javax.persistence.*;
import java.util.*;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Date {

    // 플래너 Date 인덱스
    @Id
    @GeneratedValue
    private Long dateId;

    // 공통 Todo
    @OneToMany(cascade = CascadeType.ALL)
    private List<TodoCommon> todoCommons = new ArrayList<>();

    // 숙박 Todo
    @OneToMany(cascade = CascadeType.ALL)
    private List<AccommodationTodo> accommodationTodos = new ArrayList<>();

    // 관광 Todo
    @OneToMany(cascade = CascadeType.ALL)
    private List<AttractionTodo> attractionTodos = new ArrayList<>();

    // 예산 Todo
    @OneToMany(cascade = CascadeType.ALL)
    private List<BudgetTodo> budgetTodos = new ArrayList<>();

    // 교통 Todo
    @OneToMany(cascade = CascadeType.ALL)
    private List<TransportTodo> transportTodos = new ArrayList<>();
}
