package server.domain.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.domain.planner.domain.Date;

@Repository
public interface DateRepository extends JpaRepository<Date, Long> {

    // 특정 날짜에 해당 하는 TodoId 로 찾은 투두 리스트
    Date findByTodosTodoId (Long todoId);
}
