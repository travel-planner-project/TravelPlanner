package travelplanner.project.demo.member.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.exception.ExceptionType;
import travelplanner.project.demo.global.security.jwt.JwtService;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository repository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    // 회원가입
    public AuthResponse register(RegisterRequest request) {

        Optional<Member> member = repository.findByEmail(request.getEmail());

        if (member.isPresent()) {
            throw new Exception(ExceptionType.ALREADY_EXIST_EMAIL);
        }

        if (request.getEmail() == null || request.getPassword() == null || request.getUserNickname() == null) {
            throw new Exception(ExceptionType.INVALID_INPUT_VALUE);

        } else {

            var user = Member.builder()
                    .userNickname(request.getUserNickname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.MEMBER)
                    .build();

            repository.save(user);

            var jwtToken = jwtService.generateToken(user);
            AuthResponse response = AuthResponse.builder()
                    .token(jwtToken)
                    .build();

            return response;
        }
    }


    // 로그인
    @Transactional
    public AuthResponse login(LoginRequest request) {

        Member member = repository.findByEmail(request.getEmail()).get();
        Profile profile = profileRepository.findProfileByMemberUserId(member.getUserId());

        if (profile == null) { // 특정 유저의 프로필이 없는경우

            profile = Profile.builder()
                    .member(member)
                    .build();

            profileRepository.save(profile);
        }

        profile.setProfileImgUrl("등록된 이미지가 없습니다.");
        profileRepository.save(profile);


        if (member == null) {
            throw new Exception(ExceptionType.USER_NOT_FOUND);

        } else {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow();

            var jwtToken = jwtService.generateToken(user);

            AuthResponse response = AuthResponse.builder()
                    .userId(member.getUserId())
                    .userNickname(member.getUserNickname())
                    .email(member.getEmail())
                    .token(jwtToken)
                    .profileImgUrl(profile.getProfileImgUrl())
                    .build();

            return response;
        }
    }
}
