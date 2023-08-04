package travelplanner.project.demo.planner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TravelGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 플래너 연관관계 매핑
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id")
    private Planner planner;

    // 그룹 멤버 매핑
    @OneToMany(mappedBy = "travelGroup")
    @Builder.Default
    private List<GroupMember> groupMembers = new ArrayList<>();


    public void mappingGroupMember(GroupMember groupMember) {
        groupMembers.add(groupMember);
    }

    // 그룹 멤버 추가
    public void createGroupMember(GroupMember groupMember){
        this.groupMembers.add(groupMember);
    }

    // 그룹 멤버 삭제
    public void deleteGroupMember(GroupMember groupMember){
        this.groupMembers.remove(groupMember);
    }
}
