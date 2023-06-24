package server.domain.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.domain.planner.domain.Date;

@Repository
public interface DateRepository extends JpaRepository<Date, Long> {
}
