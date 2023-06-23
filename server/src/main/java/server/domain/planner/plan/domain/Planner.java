package server.domain.planner.plan.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import server.domain.planner.travelGroup.domain.TravelGroup;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Planner {

    // 플래너 인덱스
    @Id
    @GeneratedValue
    private Long plannerId;

    // 플래너 제목
    private String planTitle;

    // 플래너 공개여부
    @ColumnDefault("false")
    private Boolean isPrivate;

    // 여행 그룹
    @OneToOne
    @JoinColumn(name = "travelGroupId")
    private TravelGroup travelGroup;

    // Date 리스트
    @OneToMany(fetch = FetchType.EAGER)
    private List<Date> dateList = new ArrayList<>();
}
