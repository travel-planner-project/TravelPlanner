package travelplanner.project.demo.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedResponse {

    @Schema(description = "플래너 인덱스", example = "1")
    private Long plannerId;

    @Schema(description = "플래너 제목", example = "부산 여행 가기")
    private String planTitle;

    @Schema(description = "여행 시작 날짜", example = "7/14")
    private LocalDateTime startDate;

    @Schema(description = "여행 끝 날짜", example = "7/20")
    private LocalDateTime endDate;

    @Schema(description = "호스트 닉네임", example = "준형")
    private String hostName;

    @Schema(description = "프로필 이미지 url")
    private String hostUrl;

}