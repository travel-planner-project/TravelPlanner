package travelplanner.project.demo.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.member.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) {

        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isEmpty()) {
            throw new UsernameNotFoundException("------------------------- 해당 유저를 찾을 수 없습니다. 유저 이메일:  " + email);
        }

        return new CustomUserDetails(member.get());
    }
}
