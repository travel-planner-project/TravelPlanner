package server.domain.planner.plan.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import server.domain.planner.plan.domain.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class DateResponse {

    // 날짜 인덱스
    private Long dateId;

    // 날짜 타이틀
    private String dateTitle;

    // Todo Responses
    private List<TodoResponse> todoResponses = new ArrayList<>();

    public DateResponse (Date entity) {

        this.dateId = entity.getDateId();
        this.dateTitle = entity.getDateTitle();
        this.todoResponses = TodoResponse.todoResponses(entity.getTodos());

    }

    public static List<DateResponse> dateResponses(List<Date> entityList) {

        return entityList.stream().map(DateResponse::new).collect(Collectors.toList());
    }
}
