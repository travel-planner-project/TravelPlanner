package travelplanner.project.demo.planner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "투두 생성 요청 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToDoCraeteRequest {

    @Schema(description = "일정 제목", example = "인스타 감성 카페가기")
    private String itemTitle;

    @Schema(description = "일정 날짜")
    private String itemTime;

    @Schema(description = "일정 카테고리")
    private String category;

    @Schema(description = "일정 주소")
    private String itemAddress;

    @Schema(description = "예산", example = "10000")
    private Long budget;

    @Schema(description = "일정 내용", example = "감귤 타르트가 유명하다고 했어!")
    private String itemContent;

    @Schema(description = "공개여부", example = "false")
    private Boolean isPrivate;
}
