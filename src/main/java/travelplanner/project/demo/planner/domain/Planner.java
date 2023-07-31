package travelplanner.project.demo.planner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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

    @Id
    @GeneratedValue
    private Long plannerId;

//    private Long userId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private Member member;

    @Builder.Default
    private String planTitle = "제목을 입력해주세요";

    @ColumnDefault("false")
    private Boolean isPrivate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToMany(mappedBy = "planner")
//    @Builder.Default
    private List<PlannerDate> plannerDates = new ArrayList<>();

    public void mappingDate(PlannerDate plannerDate) {
        plannerDates.add(plannerDate);
    }

    public PlannerEditor.PlannerEditorBuilder toEditor() {
        return PlannerEditor.builder()
                .planTitle(planTitle)
                .isPrivate(isPrivate);
    }


    public void edit(PlannerEditor plannerEditor){ // Member member
        planTitle = plannerEditor.getPlanTitle();
        isPrivate = plannerEditor.getIsPrivate();
    }

    //날짜 추가
    public void createDate(PlannerDate plannerDate){
        this.plannerDates.add(plannerDate);
    }

    //날짜 삭제
    public void deleteDate(PlannerDate plannerDate){
        this.plannerDates.remove(plannerDate);
    }
}
