package travelplanner.project.demo.planner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GroupMember {

    @Id
    private Long id;

    // 닉네임
    private String userNickname;

    // 프로필 이미지 url
    private String profileImageUrl;

    // 멤버 유형
    @Enumerated(EnumType.STRING)
    private GroupMemberType groupMemberType;

    // 여행그룹 연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id")
    private Planner planner;


    public void mappingTravelGroup(Planner planner) {
        this.planner = planner;
        planner.mappingGroupMember(this);
    }
}
