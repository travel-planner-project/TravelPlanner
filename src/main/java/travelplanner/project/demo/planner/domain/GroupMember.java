package travelplanner.project.demo.planner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelplanner.project.demo.member.profile.Profile;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이메일
    private String email;

    // 닉네임
    private String userNickname;

    // 유저 인덱스
    private Long userId;

    // 멤버 유형
    @Enumerated(EnumType.STRING)
    private GroupMemberType groupMemberType;

    // 여행그룹 연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id")
    private Planner planner;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;


    public void mappingPlanner(Planner planner) {
        this.planner = planner;
        planner.mappingGroupMember(this);
    }

}
