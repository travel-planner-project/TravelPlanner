package travelplanner.project.demo.domain.planner.calender.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import travelplanner.project.demo.domain.planner.calender.domain.Calendar;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Calendar c SET c.dateTitle = :dateTitle WHERE c.id = :id")
    void updatedateTitle(@Param("id") Long id, @Param("dateTitle") String dateTitle);

    List<Calendar> findByPlannerId(Long plannerId);
}
