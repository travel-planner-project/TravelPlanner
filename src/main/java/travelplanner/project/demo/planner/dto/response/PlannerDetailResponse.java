package travelplanner.project.demo.planner.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import travelplanner.project.demo.planner.domain.Chatting;
import travelplanner.project.demo.planner.domain.GroupMember;
import travelplanner.project.demo.planner.domain.Planner;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "플래너 상세 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlannerDetailResponse {

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


    private List<GroupMember> groupMemberList;

    @Schema(description = "채팅 리스트", example = "")
    private List<ChatResponse> chattings;

    // Todo 그룹멤버 정보 전부 추가 List<그룹멤버>
    // Todo 채팅부분 추가해야함

}
