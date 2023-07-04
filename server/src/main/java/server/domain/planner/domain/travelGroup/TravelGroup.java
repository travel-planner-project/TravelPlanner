package server.domain.planner.domain.travelGroup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TravelGroup {

    // 여행 그룹 인덱스
    @Id
    @GeneratedValue
    private Long travelGroupId;

    // 여행 그룹 멤버
    @OneToMany
    private List<GroupMember> groupMembers = new ArrayList<>();
}
