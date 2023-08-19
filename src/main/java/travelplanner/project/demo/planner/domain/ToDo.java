package travelplanner.project.demo.planner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 일정 제목
    private String itemTitle;
    // 일정 시간
    private String itemTime;
    // 일정 분류
    private String category;
    // 일정 주소
    private String itemAddress;
    // 지출 금액
    @Builder.Default
    private Long budget = 0L;

    @ColumnDefault("false")
    private Boolean isPrivate;

    private String itemContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    public void mappingDate(Calendar calendar) {
        this.calendar = calendar;
        calendar.mappingToDo(this);
    }

    public ToDoEditor.ToDoEditorBuilder toEditor() {

        return ToDoEditor.builder()
                .itemTitle(itemTitle)
                .itemTime(itemTime)
                .category(category)
                .itemAddress(itemAddress)
                .budget(budget)
                .isPrivate(isPrivate)
                .itemContent(itemContent);
    }

    public void edit(ToDoEditor toDoEditor) {
        itemTitle = toDoEditor.getItemTitle();
        itemTime = toDoEditor.getItemTime();
        category = toDoEditor.getCategory();
        itemAddress = toDoEditor.getItemAddress();
        budget = toDoEditor.getBudget();
        isPrivate = toDoEditor.getIsPrivate();
        itemContent = toDoEditor.getItemContent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDo other = (ToDo) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
