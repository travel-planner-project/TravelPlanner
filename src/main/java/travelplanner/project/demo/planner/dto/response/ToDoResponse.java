package travelplanner.project.demo.planner.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 수정과 삭제시에 보여질 Response - WebSocket
public class ToDoResponse {

    // Date FK
    private Long dateId;

    // TODOPk
    private Long itemId;

    // 일정 제목
    private String itemTitle;

    // 일정 분류
    private String category;

    // 일정 시간
    private String itemDate;

    // 갈치구이랑 김치찌개
    private String itemContent;

    // 공개 여부
    private Boolean isPrivate;

    // 지출 금액
    private Long budget;

    // 일정 주소
    private String itemAddress;


}
