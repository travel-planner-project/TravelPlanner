package travelplanner.project.demo.domain.planner.groupmember.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelplanner.project.demo.domain.planner.groupmember.domain.GroupMember;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    GroupMember findGroupMemberById(Long groupMemberId);
    List<GroupMember> findGroupMemberByPlannerId(Long PlannerId);
    List<GroupMember> findByUserId(Long userId);
    List <GroupMember> findByEmail(String email);

    // 플래너 서비스 중 삭제 메서드에서 관련된 그룹멤버들을 삭제하기 위해
    void deleteAllByPlannerId(Long plannerId);

    List<GroupMember> findByPlannerId(Long plannerId);
}
