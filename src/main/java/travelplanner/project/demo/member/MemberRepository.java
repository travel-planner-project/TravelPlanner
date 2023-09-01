package travelplanner.project.demo.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    List<Member> findMemberByEmail(String email);
    Optional<Member> findMemberByEmailAndProvider(String email, String Provider);
    Optional<Member> findById(Long id);
}
