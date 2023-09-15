package travelplanner.project.demo.domain.planner.planner.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelplanner.project.demo.domain.planner.calender.dto.response.CalendarResponse;
import travelplanner.project.demo.domain.planner.groupmember.dto.response.GroupMemberResponse;
import travelplanner.project.demo.domain.planner.planner.dto.response.PlannerDetailResponse;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "플래너 상세 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlannerDetailUnauthorizedResponse implements PlannerDetailResponse {

    @Schema(description = "플래너 인덱스", example = "1")
    private Long plannerId;

    @Schema(description = "플래너 제목", example = "제주 감귤국 탐방")
    private String planTitle;

    @Schema(description = "공개 여부", example = "false")
    private Boolean isPrivate;

    @Schema(description = "여행 시작 날짜", example = "7/14")
    private LocalDateTime startDate;

    @Schema(description = "여행 끝 날짜", example = "7/20")
    private LocalDateTime endDate;

    @Schema(description = "캘린더 집합", example = "")
    private List<CalendarResponse> calendars;

    @Schema(description = "그룹멤버 리스트", example = "")
    private List<GroupMemberResponse> groupMemberList;

}
