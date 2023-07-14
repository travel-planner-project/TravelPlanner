package server.domain.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.domain.planner.domain.travelGroup.TravelGroup;

import java.util.Optional;

@Repository
public interface TravelGroupRepository extends JpaRepository<TravelGroup, Long> {

    Optional<TravelGroup> findByTravelGroupId(Long travelGroupId);
}