package travelplanner.project.demo.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelplanner.project.demo.domain.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    List<Member> findMemberByEmail(String email);
    Optional<Member> findMemberByEmailAndProvider(String email, String Provider);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmailAndProvider(String email, String provider);

}
