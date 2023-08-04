package travelplanner.project.demo.planner.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Schema(description = "투두 응답 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 수정과 삭제시에 보여질 Response - WebSocket
public class ToDoResponse {

    @Schema(description = "날짜 인덱스", example = "1")
    private Long dateId;

    @Schema(description = "일정 인덱스", example = "인스타 감성 카페가기")
    private Long itemId;

    @Schema(description = "일정 제목", example = "인스타 감성 카페가기")
    private String itemTitle;

    @Schema(description = "일정 날짜")
    private String itemDate;

    @Schema(description = "일정 카테고리")
    private String category;

    @Schema(description = "일정 주소")
    private String itemAddress;

    @Schema(description = "예산", example = "10000")
    private Long budget;

    @Schema(description = "일정 내용", example = "감귤 타르트가 유명하다고 했어!")
    private String content;

    @Schema(description = "공개여부", example = "false")
    private Boolean isPrivate;


}
