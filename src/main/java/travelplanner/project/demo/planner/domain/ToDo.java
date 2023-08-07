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
    @GeneratedValue
    private Long id;

    // 일정 제목
    private String itemTitle;
    // 일정 시간
    private String itemDate;
    // 일정 분류
    private String category;
    // 일정 주소
    private String itemAddress;
    // 지출 금액
    @Builder.Default
    private Long budget = 0L;

    @ColumnDefault("false")
    private Boolean isPrivate;

    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    public void mappingDate(Calendar calendar) {
        this.calendar = calendar;
        calendar.mappingToDo(this);
    }

    public ToDoEditor.ToDoEditorBuilder toEditor() {

        return ToDoEditor.builder()
                .itemTitle(itemTitle)
                .itemDate(itemDate)
                .category(category)
                .itemAddress(itemAddress)
                .budget(budget)
                .isPrivate(isPrivate)
                .content(content);
    }

    public void edit(ToDoEditor toDoEditor) {
        itemTitle = toDoEditor.getItemTitle();
        itemDate = toDoEditor.getItemDate();
        category = toDoEditor.getCategory();
        itemAddress = toDoEditor.getItemAddress();
        budget = toDoEditor.getBudget();
        isPrivate = toDoEditor.getIsPrivate();
        content = toDoEditor.getContent();
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
