package travelplanner.project.demo.member.socialauth.google;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.auth.AuthResponse;
import travelplanner.project.demo.member.auth.AuthService;
import travelplanner.project.demo.member.auth.LoginRequest;
import travelplanner.project.demo.member.auth.Role;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuthService {

    private final GoogleOAuth googleOAuth;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final AuthService authService;


    private GoogleUserInfoDto getGoogleUserInfoDto(String code) throws JsonProcessingException {

        ResponseEntity<String> accessTokenResponse = googleOAuth.requestAccessToken(code);
        GoogleOAuthTokenResponse oAuthTokenDto = googleOAuth.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = googleOAuth.requestUserInfo(oAuthTokenDto);

        return googleOAuth.getUserInfo(userInfoResponse);
    }


    // 구글 로그인
    @Transactional
    public AuthResponse googleLogin(String code, HttpServletResponse response) throws IOException {

        // 유저 정보 가져오기
        GoogleUserInfoDto googleUserInfoDto = getGoogleUserInfoDto(code);

        log.info("구글 로그인 서비스: "  + googleUserInfoDto.getEmail());

        // 이메일이 없다면 회원가입 처리
        if (memberRepository.findByEmail(googleUserInfoDto.getEmail()).isEmpty()) {

            Member user = Member.builder()
                    .email(googleUserInfoDto.getEmail())
                    .userNickname(googleUserInfoDto.getName())
                    .password(passwordEncoder.encode("google"))
                    .role(Role.MEMBER)
                    .build();

            memberRepository.save(user);

            Profile profile = Profile.builder()
                    .keyName("")
                    .profileImgUrl(googleUserInfoDto.getPicture())
                    .member(user)
                    .build();

//            member.setProfile(profile);

            profileRepository.save(profile);


        }

        // 로그인 처리
        LoginRequest request = new LoginRequest();
        request.setEmail(googleUserInfoDto.getEmail());
        request.setPassword("google");

        return authService.login(request, response);
    }
}
