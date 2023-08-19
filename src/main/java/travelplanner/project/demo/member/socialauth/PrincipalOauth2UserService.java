package travelplanner.project.demo.member.socialauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;
import travelplanner.project.demo.member.auth.Role;
import travelplanner.project.demo.member.profile.Profile;
import travelplanner.project.demo.member.profile.ProfileRepository;
import travelplanner.project.demo.member.socialauth.kakao.KakaoUserInfo;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Value("${secret.key}")
    private String SECRET_KEY;

    // Access 토큰 유효시간 15 분
    static final long AccessTokenValidTime = 5 * 60 * 1000L;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private  ProfileRepository profileRepository;
    @Autowired
    private @Lazy PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(request);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;

        String provider = request.getClientRegistration().getRegistrationId();
/*
        if(provider.equals("google")) {
            log.info("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo( oAuth2User.getAttributes() );
        }*/  if(provider.equals("kakao")) {
            log.info("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo( (Map)oAuth2User.getAttributes() );
        } /*else if(provider.equals("naver")) {
            log.info("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo( (Map)oAuth2User.getAttributes().get("response") );
        } */
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String profileUrl = oAuth2UserInfo.getProfile();
        String nickname = oAuth2UserInfo.getName();

        // 회원가입 유무 확인
        Optional<Member> optionalUser = memberRepository.findByEmail(email);
        Member member = null;

        // 없다면 회원가입
        if(optionalUser.isEmpty()) {

            Member user = Member.builder()
                    .email(email)
                    .userNickname(nickname)
                    .password(passwordEncoder.encode("kakao"))
                    .role(Role.MEMBER)
                    .build();

            memberRepository.save(user);

            Profile profile = Profile.builder()
                    .keyName("")
                    .profileImgUrl(profileUrl)
                    .member(user)
                    .build();

            profileRepository.save(profile);

        }else{
            member = optionalUser.get();
        }

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }

}
