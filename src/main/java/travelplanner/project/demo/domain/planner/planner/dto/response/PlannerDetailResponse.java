package travelplanner.project.demo.domain.planner.planner.dto.response;

import travelplanner.project.demo.domain.planner.calender.dto.response.CalendarResponse;
import travelplanner.project.demo.domain.planner.groupmember.dto.response.GroupMemberResponse;

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
