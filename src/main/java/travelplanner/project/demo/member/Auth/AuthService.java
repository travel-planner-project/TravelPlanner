package travelplanner.project.demo.member.Auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.CookieUtil;
import travelplanner.project.demo.global.util.RedisUtil;
import travelplanner.project.demo.global.util.TokenUtil;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;


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


    // 회원가입
    @Transactional
    public void register(RegisterRequest request) throws ApiException {

        // 이메일로 멤버 조회
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException(ErrorType.ALREADY_EXIST_EMAIL));

        if (request.getEmail() == null || request.getPassword() == null || request.getUserNickname() == null) {
            throw new ApiException(ErrorType.NULL_VALUE_EXIST);
        }

        // 멤버 생성
        Member user = Member.builder()
                .userNickname(request.getUserNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.MEMBER)
                .build();

        memberRepository.save(user);

        // 프로필 생성
        Profile profile = Profile.builder()
                .member(user)
                .build();

        profileRepository.save(profile);
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
        String refreshToken = tokenUtil.generateRefreshToken(member.getEmail());

        // 어세스 토큰은 헤더에 담아서 응답으로 보냄
        response.setHeader("Authorization", accessToken);

        // 리프레시 토큰은 쿠키에 담아서 응답으로 보냄
        cookieUtil.create(refreshToken, response);

        // 리프레시 토큰을 Redis 에 저장
        redisUtil.setData(member.getEmail(), refreshToken);

        return AuthResponse.builder()
                .userId(member.getId())
                .email(member.getEmail())
                .userNickname(member.getUserNickname())
                .profileImgUrl(profile.getProfileImgUrl())
                .build();
    }
}
