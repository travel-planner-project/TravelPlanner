package travelplanner.project.demo.member.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.AuthUtil;
import travelplanner.project.demo.global.util.CookieUtil;
import travelplanner.project.demo.global.util.RedisUtil;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;

import java.time.Duration;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtil tokenUtil;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;
    private final AuthUtil authUtil;
    private final AuthenticationManager authenticationManager;


    // 회원가입
    @Transactional
    public void register(RegisterRequest request) throws ApiException {

        // 이메일로 멤버 조회
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());

        if (member.isPresent()) {
            throw new ApiException(ErrorType.ALREADY_EXIST_EMAIL);
        }

        if (request.getEmail() == null || request.getPassword() == null || request.getUserNickname() == null) {
            throw new ApiException(ErrorType.NULL_VALUE_EXIST);
        }

        // 멤버 생성 및 저장
        Member user = Member.builder()
                .userNickname(request.getUserNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.MEMBER)
                .build();

        memberRepository.save(user);  // 변경된 user 저장

        // 프로필 생성
        Profile profile = Profile.builder()
                .keyName("")
                .profileImgUrl("")
                .member(user)
                .build();
//        user.setProfile(profile);  // 양방향 연관관계 설정

        profileRepository.save(profile);  // profile 저장

    }


    // 로그인
    @Transactional
    public AuthResponse login(LoginRequest request, HttpServletResponse response) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        Profile profile = profileRepository.findProfileByMemberId(member.getId());

        // 인증이 성공했을 때, 어세스 토큰과 리프레시 토큰 발급
        String accessToken = tokenUtil.generateAccessToken(member.getEmail());

        // 어세스 토큰은 헤더에 담아서 응답으로 보냄
        response.setHeader("Authorization", accessToken);

        if (redisUtil.getData(member.getEmail()) == null) { // 레디스에 토큰이 저장되어 있지 않은 경우

            String refreshToken = tokenUtil.generateRefreshToken(member.getEmail());

            // 리프레시 토큰은 쿠키에 담아서 응답으로 보냄
            cookieUtil.create(refreshToken, response);
            redisUtil.setDataExpire(member.getEmail(), refreshToken, Duration.ofMinutes(3));

        } else { // 레디스에 토큰이 저장되어 있는 경우

            String refreshToken = redisUtil.getData(member.getEmail());
            cookieUtil.create(refreshToken, response);
        }

        return AuthResponse.builder()
                .userId(member.getId())
                .email(member.getEmail())
                .userNickname(member.getUserNickname())
                .profileImgUrl(profile.getProfileImgUrl())
                .build();
    }


    // 로그아웃
    @Transactional
    public void logout(HttpServletResponse response) {

        // 어세스토큰 삭제
        response.setHeader("Authorization", "");
        // 쿠키 삭제
        cookieUtil.delete("", response);
    }
}
