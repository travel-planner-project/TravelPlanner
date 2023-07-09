package server.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import server.domain.user.domain.User;
import server.domain.user.repository.UserRepository;
import server.global.code.ErrorCode;
import server.global.exception.HandlableException;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    // DB에서 사용자 인증 정보를 가져오는 역할
    // 토큰에 저장된 유저 정보를 활용할 수 있음

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_USER));

        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUser(user);

        return userDetails;
    }
}
