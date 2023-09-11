package travelplanner.project.demo.domain.planner.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelplanner.project.demo.domain.planner.todo.domain.ToDo;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findByCalendarId(Long calendarId);
}
