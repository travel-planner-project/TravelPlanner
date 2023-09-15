package travelplanner.project.demo.domain.planner.chat.repository;

import org.springframework.data.repository.CrudRepository;
import travelplanner.project.demo.domain.planner.chat.domain.Chatting;

import java.time.LocalDateTime;
import java.util.List;

public interface ChattingRepository extends CrudRepository<Chatting, Long> {

    List<Chatting> findTop100ByCreatedAtBefore(LocalDateTime createdAt);

    List<Chatting> findByPlannerId(Long plannerId);

}

