package travelplanner.project.demo.domain.planner.planner.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.planner.planner.domain.Planner;

import java.util.List;


public interface PlannerRepository extends JpaRepository<Planner, Long> {
    Page<Planner> findPlannerByMemberId(Long userId, Pageable pageable);
    Planner findPlannerById(Long plannerId);
    List<Planner> findByMemberOrderByIdDesc(Member member);
    Page<Planner> findByIsPrivateFalseOrderByIdDesc(Pageable pageable);
    Page<Planner> findByIsPrivateFalseAndPlanTitleContainingOrderByIdDesc(String planTitle, Pageable pageable);

}
