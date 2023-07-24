package travelplanner.project.demo.planner.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToDoRequest {

    // 일정 제목
    private String itemTitle;
    // 일정 시간
    private LocalDateTime itemDate;
    // 일정 분류
    private String category;
    // 일정 주소
    private String itemAddress;
    // 지출 금액
    private Long budget;
    // 투두 내용
    private String content;
    // 여행 비공개 공개 여부
    private Boolean isPrivate;

}
