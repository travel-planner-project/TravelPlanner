package server.domain.planner.repository.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.domain.planner.domain.Todo.AttractionTodo;

@Repository
public interface AttractionRepository extends JpaRepository<AttractionTodo, Long> {

}
