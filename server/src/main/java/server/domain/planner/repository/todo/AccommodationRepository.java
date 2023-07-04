package server.domain.planner.repository.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.domain.planner.domain.Todo.AccommodationTodo;

@Repository
public interface AccommodationRepository extends JpaRepository<AccommodationTodo, Long> {

}
