package travelplanner.project.demo.planner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import travelplanner.project.demo.member.Member;
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

    @Id
    @GeneratedValue
    private Long plannerId;

//    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @Builder.Default
    private String planTitle = "제목을 입력해주세요";

    @ColumnDefault("false")
    private Boolean isPrivate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToMany(fetch = FetchType.LAZY)
    @Builder.Default
    private List<Date> dates = new ArrayList<>();

    public PlannerEditor.PlannerEditorBuilder toEditor() {
        return PlannerEditor.builder()
                .planTitle(planTitle)
                .isPrivate(isPrivate);
    }


    public void edit(PlannerEditor plannerEditor, Member member){
        planTitle = plannerEditor.getPlanTitle();
        isPrivate = plannerEditor.getIsPrivate();
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
