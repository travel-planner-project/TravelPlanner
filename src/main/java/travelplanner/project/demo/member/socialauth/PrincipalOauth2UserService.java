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
import travelplanner.project.demo.member.socialauth.google.GoogleUserInfo;
import travelplanner.project.demo.member.socialauth.kakao.KakaoUserInfo;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(request);
        log.info("------------------ getAttributes : {}", oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;

        String provider = request.getClientRegistration().getRegistrationId();

        if(provider.equals("google")) {
            log.info("------------------ 구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else  if(provider.equals("kakao")) {
            log.info("------------------ 카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } /*else if(provider.equals("naver")) {
            log.info("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo( (Map)oAuth2User.getAttributes().get("response") );
        } */
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String profileUrl = oAuth2UserInfo.getProfile();
        String nickname = oAuth2UserInfo.getName();

        // 회원가입 유무 확인
        Optional<Member> member = memberRepository.findByEmail(email);

        // 없다면 회원가입
        if(member.isEmpty()) {

            String password;

            if (provider.equals("google")) {
                password = "google";
            } else if (provider.equals("kakao")) {
                password = "kakao";
            } else {
                password = "naver";
            }

            Member newMember = Member.builder()
                    .email(email)
                    .userNickname(nickname)
                    .password(passwordEncoder.encode(password))
                    .role(Role.MEMBER)
                    .provider(provider)
                    .providerId(providerId)
                    .profile(Profile.builder()
                            .keyName("")
                            .profileImgUrl(profileUrl)
                            .build())
                    .build();

            memberRepository.save(newMember);

            return new PrincipalDetails(newMember, oAuth2User.getAttributes());
        }

        return new PrincipalDetails(member.get(), oAuth2User.getAttributes());
    }

}
