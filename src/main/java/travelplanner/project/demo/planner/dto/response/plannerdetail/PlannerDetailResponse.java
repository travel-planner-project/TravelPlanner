package travelplanner.project.demo.planner.dto.response.plannerdetail;

import travelplanner.project.demo.planner.dto.response.CalendarResponse;
import travelplanner.project.demo.planner.dto.response.GroupMemberResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface PlannerDetailResponse {

    Long getPlannerId();
    String getPlanTitle();
    Boolean getIsPrivate();
    LocalDateTime getStartDate();
    LocalDateTime getEndDate();
    List<CalendarResponse> getCalendars();
    List<GroupMemberResponse> getGroupMemberList();
}
