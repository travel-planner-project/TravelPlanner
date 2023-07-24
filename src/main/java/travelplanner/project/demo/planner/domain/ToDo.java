package travelplanner.project.demo.planner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

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
    private LocalDateTime itemDate;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "date_id")
    private Date date;

    public void mappingDate(Date date) {
        this.date = date;
        date.mappingToDo(this);
    }
}
