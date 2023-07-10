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
