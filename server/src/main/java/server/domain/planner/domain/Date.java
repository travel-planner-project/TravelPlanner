package server.domain.planner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.planner.domain.Todo.*;
import server.domain.planner.plan.domain.Todo.*;
import server.domain.planner.dto.request.DateCreateRequest;
import server.domain.planner.dto.request.DateUpdateRequest;

import javax.persistence.*;
import java.util.*;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Date {

    // 플래너 Date 인덱스
    @Id
    @GeneratedValue
    private Long dateId;

    // 날짜 제목
    private String dateTitle;

    // 공통 Todo
    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private List<Todo> todos = new ArrayList<>();

    // 숙박 Todo
    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private List<AccommodationTodo> accommodationTodos = new ArrayList<>();

    // 관광 Todo
    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private List<AttractionTodo> attractionTodos = new ArrayList<>();

    // 예산 Todo
    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private List<BudgetTodo> budgetTodos = new ArrayList<>();

    // 교통 Todo
    @OneToMany(cascade = CascadeType.ALL)
    private List<TransportTodo> transportTodos = new ArrayList<>();




    // 날짜 추가
    public static Date createDateList (DateCreateRequest request) {

        return Date.builder()
                .dateTitle(request.getDateTitle()).build();
    }

    // 날짜 수정
    public void updateDateList (DateUpdateRequest request) {

        this.dateTitle = request.getDateTitle();
    }




    // 투두 추가
    public void createTodoCommon(Todo todoComomon) {
        todos.add(todoComomon);
    }

    public void createAccommodationTodo (AccommodationTodo accommodationTodo) {
        accommodationTodos.add(accommodationTodo);
    }

    public void createAttractionTodo (AttractionTodo attractionTodo) {
        attractionTodos.add(attractionTodo);
    }

    public void createBudgetTodo (BudgetTodo budgetTodo) {
        budgetTodos.add(budgetTodo);
    }

    public void createTransportTodo (TransportTodo transportTodo) {
        transportTodos.add(transportTodo);
    }

    // 투두 삭제
    public void deleteTodo (Todo todo) {
        todos.remove(todo);
    }

    public void deleteAccommodationTodo (AccommodationTodo accommodationTodo) {
        accommodationTodos.remove(accommodationTodo);
    }

    public void deleteAttractionTodo (AttractionTodo attractionTodo) {
        attractionTodos.remove(attractionTodo);
    }

    public void deleteBudgetTodo (BudgetTodo budgetTodo) {
        budgetTodos.remove(budgetTodo);
    }

    public void deleteTransportTodo (TransportTodo transportTodo) {
        transportTodos.remove(transportTodo);
    }
}
