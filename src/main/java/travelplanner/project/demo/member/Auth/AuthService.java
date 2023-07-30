package travelplanner.project.demo.member.Auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenUtil tokenUtil;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;
    private final AuthenticationManager authenticationManager;
    private Authentication authentication;


    // 회원가입
    @Transactional
    public void register(RegisterRequest request) throws Exception{

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
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .role(Role.MEMBER)
                .build();

        memberRepository.save(user);
    }


    // 로그인
    @Transactional
    public AuthResponse login(LoginRequest request, HttpServletResponse response) throws Exception {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Optional<Member> member = memberRepository.findByEmail(request.getEmail());

        // 프로필
        Profile profile = profileRepository.findProfileByMemberUserId(member.get().getUserId());

        if (profile == null) {

            profile = Profile.builder()
                    .member(member.get())
                    .build();

            profileRepository.save(profile);
        }

        // 입력한 비밀번호와 사용자의 비밀번호를 비교하여 인증
        if (!bCryptPasswordEncoder.matches(request.getPassword(), member.get().getPassword())) {
            throw new Exception(ExceptionType.PASSWORD_IS_NOT_MATCHED);

        } else {

            // 인증이 성공했을 때, 어세스 토큰과 리프레시 토큰 발급
            String accessToken = tokenUtil.generateAccessToken(member.get().getEmail());
            String refreshToken = tokenUtil.generateRefreshToken(member.get().getEmail());

            // 어세스 토큰과 리프레시 토큰을 쿠키에 담아서 응답으로 보냄
            Cookie accessTokenCookie = cookieUtil.create("accessToken", accessToken);
            response.addCookie(accessTokenCookie);

            // 리프레시 토큰은 쿠키에 담아서 응답으로 보냄
            Cookie refreshTokenCookie = cookieUtil.create("refreshToken", refreshToken);
            response.addCookie(refreshTokenCookie);
        }

        return AuthResponse.builder()
                .userId(member.get().getUserId())
                .email(member.get().getEmail())
                .userNickname(member.get().getUserNickname())
                .profileImgUrl(profile.getProfileImgUrl())
                .build();
    }
}
