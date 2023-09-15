package travelplanner.project.demo.domain.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelplanner.project.demo.domain.profile.domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findProfileByMemberId(Long userId);
}
