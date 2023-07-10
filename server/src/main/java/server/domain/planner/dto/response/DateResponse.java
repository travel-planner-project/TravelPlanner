package server.domain.planner.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.domain.planner.domain.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class DateResponse {

    @ApiModelProperty(example = "1")
    private Long dateId;

    @ApiModelProperty(example = "날짜 제목")
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
