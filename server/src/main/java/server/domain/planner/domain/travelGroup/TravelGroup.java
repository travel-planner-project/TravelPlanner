package server.domain.planner.domain.travelGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
public class TravelGroup {

    // 여행 그룹 인덱스
    @Id
    @GeneratedValue
    private Long travelGroupId;

    // 여행 그룹 멤버
    @OneToMany
    private List<GroupMember> groupMembers = new ArrayList<>();


    public void addMembers(GroupMember groupMember) {
        groupMembers.add(groupMember);
    }

    public void removeMember(GroupMember groupMember) {
        groupMembers.remove(groupMember);
    }
}