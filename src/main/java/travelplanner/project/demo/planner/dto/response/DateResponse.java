package travelplanner.project.demo.planner.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelplanner.project.demo.planner.domain.Calendar;

@Schema(description = "날짜 생성 응답 DTO")
@Getter
@NoArgsConstructor
public class DateResponse {

    @Schema(description = "날짜 인덱스", example = "1")
    private Long id;

    @Schema(description = "날짜")
    private Calendar calendar;

//    public DateResponse(Date date) {
//        this.id = date.getId();
//        this.date = date.getScheduleItemList();
//    }
//
//    public static List<DateResponse> dateResponses(List<Date> entityList) {
//        return entityList.stream().map(DateResponse::new).collect(Collectors.toList());
//    }

}
