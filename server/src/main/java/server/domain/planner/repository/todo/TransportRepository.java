package server.domain.planner.repository.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.domain.planner.domain.Todo.TransportTodo;

@Repository
public interface TransportRepository extends JpaRepository<TransportTodo, Long> {

}
