package travelplanner.project.demo.member.socialauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import travelplanner.project.demo.member.socialauth.google.GoogleUserInfo;
import travelplanner.project.demo.member.socialauth.kakao.KakaoUserInfo;

import java.util.Map;

@Slf4j
public class OAuthUserInfoFactory {
    public static OAuth2UserInfo getOAuthUserInfo (String provider, Map<String, Object> attributes) {

        if (provider.equals("google")) {
            log.info("------------------ 구글 로그인 요청");
            return new GoogleUserInfo(attributes);

        } else if (provider.equals("kakao")) {
            log.info("------------------ 카카오 로그인 요청");
            return new KakaoUserInfo(attributes);
        }

        return null;
    }
}
