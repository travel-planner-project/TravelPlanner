package travelplanner.project.demo.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelplanner.project.demo.planner.domain.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
}
