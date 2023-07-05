package server.domain.planner.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.domain.planner.domain.Planner;

@Repository
public interface PlannerRepository extends JpaRepository<Planner, Long> {

    Page<Planner> findByUserId (Long userId, Pageable pageable);
}
