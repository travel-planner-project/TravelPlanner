package travelplanner.project.demo.planner.chat;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import travelplanner.project.demo.planner.chat.domain.Chatting;

import java.time.LocalDateTime;

public interface ChattingRepository extends CrudRepository<Chatting, Long> {
    @Query("DELETE FROM Chatting c WHERE c.createdAt < :date")
    @Modifying
    void deleteAllByCreatedAtBefore(@Param("date") LocalDateTime date);
}

