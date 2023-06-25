package server.domain.planner.repository.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.domain.planner.domain.Todo.BudgetTodo;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetTodo, Long> {

}
