package travelplanner.project.demo.domain.message.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import travelplanner.project.demo.domain.message.domain.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(
                    "SELECT m " +
                    "FROM Message m " +
                            "WHERE m.senderUserId = :userId " +
                                    "OR m.receivedUserId = :userId"
    )
    List<Message> findAllMessagesByUserId(@Param("userId") Long userId);
}
