package travelplanner.project.demo.member.socialauth;

import lombok.extern.slf4j.Slf4j;
import travelplanner.project.demo.member.socialauth.userInfo.GoogleUserInfo;
import travelplanner.project.demo.member.socialauth.userInfo.KakaoUserInfo;
import travelplanner.project.demo.member.socialauth.userInfo.NaverUserInfo;

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

        } else if (provider.equals("naver")) {
            log.info("------------------ 네이버 로그인 요청");
            return new NaverUserInfo(attributes);
        }

        return null;
    }
}
