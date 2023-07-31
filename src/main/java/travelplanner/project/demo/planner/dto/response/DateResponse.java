package travelplanner.project.demo.planner.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import travelplanner.project.demo.planner.domain.PlannerDate;

@Getter
@NoArgsConstructor
public class DateResponse {
    private Long id;

    private PlannerDate plannerDate;

//    public DateResponse(Date date) {
//        this.id = date.getId();
//        this.date = date.getToDoList();
//    }
//
//    public static List<DateResponse> dateResponses(List<Date> entityList) {
//        return entityList.stream().map(DateResponse::new).collect(Collectors.toList());
//    }

}
