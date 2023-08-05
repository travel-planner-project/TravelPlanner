package travelplanner.project.demo.planner.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.planner.domain.Planner;

import java.util.List;


public interface PlannerRepository extends JpaRepository<Planner, Long> {
    Page<Planner> findPlannerByMemberId(Long userId, Pageable pageable);
    Planner findPlannerById(Long plannerId);

    List<Planner> findByMember(Member member);
}
