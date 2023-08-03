package travelplanner.project.demo.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelplanner.project.demo.planner.domain.GroupMember;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    GroupMember findGroupMemberById(Long groupMemberId);
    List<GroupMember> findGroupMemberByTravelGroupId(Long travelGroupId);
}
