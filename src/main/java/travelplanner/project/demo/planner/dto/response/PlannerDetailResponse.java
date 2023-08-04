package travelplanner.project.demo.planner.dto.response;

import lombok.*;
import travelplanner.project.demo.planner.domain.Planner;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlannerDetailResponse {

    private Long plannerId;

    private String planTitle;

    private Boolean isPrivate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    // Todo 그룹멤버 정보 전부 추가 List<그룹멤버>
    // Todo 채팅부분 추가해야함


}
