package server.domain.planner.domain.Todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Todo {

    // 투두 인덱스
    @Id
    @GeneratedValue
    private Long todoId;

    // 투두 타입 (enum)
    @Enumerated(EnumType.STRING)
    private TodoType todoType;

    // 투두 제목
    protected String todoTitle;

    // 투두 실행 날짜
    @Column(columnDefinition = "timestamp default now()")
    protected LocalDateTime todoDate;

    // 투두 내용
    protected String todoContent;

    // 투두 공개 여부
    @ColumnDefault("false")
    protected Boolean isPrivate;

}
