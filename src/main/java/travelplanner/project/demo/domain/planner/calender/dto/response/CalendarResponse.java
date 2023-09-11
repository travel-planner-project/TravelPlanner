package travelplanner.project.demo.domain.planner.calender.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import travelplanner.project.demo.domain.planner.todo.dto.response.ToDoResponse;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarResponse {

    private Long dateId;
    private String dateTitle;
    @CreatedDate
    private LocalDateTime createAt;
    private Long plannerId;
    private List<ToDoResponse> scheduleItemList;

    public static class CalendarResponseBuilder {
        private List<ToDoResponse> scheduleItemList;
        public CalendarResponseBuilder scheduleItemList(List<ToDoResponse> scheduleItemList) {
            this.scheduleItemList = scheduleItemList;
            return this;
        }
    }
}
