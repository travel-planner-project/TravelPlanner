package travelplanner.project.demo.planner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import travelplanner.project.demo.planner.dto.request.PlannerCreateRequest;
import travelplanner.project.demo.planner.dto.request.PlannerUpdateRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Planner {

    private Long userId;

    @Id
    @GeneratedValue
    private Long plannerId;

    @Builder.Default
    private String planTitle = "제목을 입력해주세요";

    @ColumnDefault("false")
    private Boolean isPrivate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private List<Date> dates = new ArrayList<>();

   //추후에 그룹 멤버 작성


    //플래너 생성
    public static Planner createPlanner (PlannerCreateRequest request) {
        return Planner.builder()
                .userId(request.getUserId())
                .planTitle(request.getPlanTitle())
                .isPrivate(request.getIsPrivate())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }

    //플래너 수정
    public void updatePlanner (PlannerUpdateRequest request){
        this.planTitle = request.getPlanTitle();
        this.isPrivate = request.getIsPrivate();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
    }

    //날짜 추가
    public void createDate(Date date){
        this.dates.add(date);
    }

    //날짜 삭제
    public void deleteDate(Date date){
        this.dates.remove(date);
    }
}
