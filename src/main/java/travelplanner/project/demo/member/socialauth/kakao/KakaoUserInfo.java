package travelplanner.project.demo.member.socialauth.kakao;

import lombok.AllArgsConstructor;
import travelplanner.project.demo.member.socialauth.OAuth2UserInfo;

import java.util.Map;

@AllArgsConstructor
public class KakaoUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProfile() {
        return (String) ((Map) attributes.get("properties")).get("profile_image");
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("kakao_account")).get("email");
    }

    @Override
    public String getName() {
        return  (String) ((Map) attributes.get("properties")).get("nickname");
    }

    @Override
    public String getPassword(){ return "kakao";}
}

