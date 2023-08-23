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
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dateTitle;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @Builder.Default
    private List<ToDo> scheduleItemList = new ArrayList<>();

    public void mappingToDo(ToDo toDo) {
        scheduleItemList.add(toDo);
    }

    // 플래너 연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id")
    private Planner planner;

    public void mappingPlanner(Planner planner) {
        this.planner = planner;
        planner.mappingCalendar(this);
    }

    public void edit(CalendarEditor calendarEditor){ // Member member
        dateTitle = calendarEditor.getDateTitle();
    }

    public CalendarEditor.CalendarEditorBuilder toEditor() {
        return CalendarEditor.builder()
                .dateTitle(dateTitle);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calendar other = (Calendar) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
