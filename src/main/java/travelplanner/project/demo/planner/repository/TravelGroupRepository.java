package travelplanner.project.demo.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelplanner.project.demo.planner.domain.TravelGroup;

public interface TravelGroupRepository extends JpaRepository<TravelGroup, Long> {

    TravelGroup findTravelGroupByPlannerId(Long plannerId);
}
