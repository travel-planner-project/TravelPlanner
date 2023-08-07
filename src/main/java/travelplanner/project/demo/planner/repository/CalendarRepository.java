package travelplanner.project.demo.planner.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import travelplanner.project.demo.planner.domain.Calendar;

import java.time.LocalDate;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Calendar c SET c.eachDate = :eachDate WHERE c.id = :id")
    void updateEachDate(@Param("id") Long id, @Param("eachDate") String eachDate);

    List<Calendar> findByPlannerId(Long plannerId);
}
