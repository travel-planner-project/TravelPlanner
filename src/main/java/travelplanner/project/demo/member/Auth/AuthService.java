package travelplanner.project.demo.member.Auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.Exception;
import travelplanner.project.demo.global.exception.ExceptionType;
import travelplanner.project.demo.global.security.CustomUserDetails;
import travelplanner.project.demo.global.util.CookieUtil;
import travelplanner.project.demo.global.util.RedisUtil;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtil tokenUtil;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;
    private final AuthenticationManager authenticationManager;
    private Authentication authentication;


    // 회원가입
    @Transactional
    public void register(RegisterRequest request) throws Exception {

        // 이메일로 멤버 조회
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());
        if (member.isPresent()) {
            throw new Exception(ExceptionType.ALREADY_EXIST_EMAIL);
        }

        if (request.getEmail() == null || request.getPassword() == null || request.getUserNickname() == null) {
            throw new Exception(ExceptionType.INVALID_INPUT_VALUE);
        }

        // 멤버 생성
        Member user = Member.builder()
                .userNickname(request.getUserNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.MEMBER)
                .build();

        memberRepository.save(user);
    }


    // 로그인
    @Transactional
    public AuthResponse login(LoginRequest request, HttpServletResponse response) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (InsufficientAuthenticationException e) {
            System.out.println(e.getMessage());
        }


        Optional<Member> member = memberRepository.findByEmail(request.getEmail());

        // 프로필
        Profile profile = profileRepository.findProfileByMemberId(member.get().getId());

        if (profile == null) {

            profile = Profile.builder()
                    .member(member.get())
                    .build();

            profileRepository.save(profile);
        }

        // 인증이 성공했을 때, 어세스 토큰과 리프레시 토큰 발급
        String accessToken = tokenUtil.generateAccessToken(member.get().getEmail());
        String refreshToken = tokenUtil.generateRefreshToken(member.get().getEmail());

        // 어세스 토큰은 헤더에 담아서 응답으로 보냄
        response.setHeader("Authorization", accessToken);

        // 리프레시 토큰은 쿠키에 담아서 응답으로 보냄
        cookieUtil.create(refreshToken, response);

        // 리프레시 토큰을 Redis 에 저장
        redisUtil.setData(member.get().getEmail(), refreshToken);

        return AuthResponse.builder()
                .userId(member.get().getId())
                .email(member.get().getEmail())
                .userNickname(member.get().getUserNickname())
                .profileImgUrl(profile.getProfileImgUrl())
                .build();
    }
}
