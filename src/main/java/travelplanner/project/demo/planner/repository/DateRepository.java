package travelplanner.project.demo.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelplanner.project.demo.planner.domain.Date;

public interface DateRepository extends JpaRepository<Date, Long> {
}
