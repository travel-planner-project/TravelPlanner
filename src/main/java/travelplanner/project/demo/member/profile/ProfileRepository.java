package travelplanner.project.demo.member.profile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findProfileByMemberUserId(Long userId);
}
