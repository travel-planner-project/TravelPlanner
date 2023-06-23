package server.domain.planner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.planner.dto.request.PlannerCreateRequest;
import server.domain.planner.dto.request.PlannerUpdateRequest;
import server.domain.planner.travelGroup.domain.TravelGroup;

import javax.persistence.*;
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

    // 플래너 인덱스
    @Id
    @GeneratedValue
    private Long plannerId;

    // 플래너 제목
    @Builder.Default
    private String planTitle = "제목을 입력해주세요";

    // 플래너 공개여부
    @ColumnDefault("false")
    private Boolean isPrivate;

    // 여행 그룹
    @OneToOne
    @JoinColumn(name = "travelGroupId")
    private TravelGroup travelGroup;

    // Date 리스트
    @OneToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private List<Date> dates = new ArrayList<>();




    // 플래너 생성
    public static Planner createPlanner (PlannerCreateRequest request) {

        return Planner.builder()
                .planTitle(request.getPlanTitle())
                .isPrivate(request.getIsPrivate())
                .build();
    }




    // 날짜 추가
    public void createDate (Date date) {
        this.dates.add(date);
    }

    // 날짜 삭제
    public void deleteDate (Date date) {
        this.dates.remove(date);
    }


    // 플래너 수정
    public void updatePlanner (PlannerUpdateRequest request) {
        this.planTitle = request.getPlanTitle();
        this.isPrivate = request.getIsPrivate();
    }
}
