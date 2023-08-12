package travelplanner.project.demo.planner.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import travelplanner.project.demo.planner.domain.Chatting;

import java.time.LocalDateTime;
import java.util.List;

public interface ChattingRepository extends CrudRepository<Chatting, Long> {

    List<Chatting> findTop100ByCreatedAtBefore(LocalDateTime createdAt);

}

