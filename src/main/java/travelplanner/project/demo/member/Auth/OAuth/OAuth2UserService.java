package travelplanner.project.demo.member.Auth.OAuth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import travelplanner.project.demo.global.security.PrincipalDetails;
import travelplanner.project.demo.member.Auth.Role;
import travelplanner.project.demo.member.Member;
import travelplanner.project.demo.member.MemberRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser (OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String loginId = provider + "_" + providerId;

        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        Member member;

        if (optionalMember.isEmpty()) {

            member = Member.builder()
                    .loginId(loginId)
                    .userNickname(oAuth2User.getAttribute("name"))
                    .provider(provider)
                    .providerId(providerId)
                    .role(Role.MEMBER)
                    .build();

            memberRepository.save(member);

        } else {
            member = optionalMember.get();
        }

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
