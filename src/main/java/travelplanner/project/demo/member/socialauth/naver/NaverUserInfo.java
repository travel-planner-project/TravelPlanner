package travelplanner.project.demo.member.socialauth.naver;

import lombok.AllArgsConstructor;
import travelplanner.project.demo.member.socialauth.OAuth2UserInfo;

import java.util.Map;

@AllArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProfile() {
        return (String) ((Map) attributes.get("response")).get("profile_image");
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("response")).get("email");
    }

    @Override
    public String getName() {
        return (String) ((Map) attributes.get("response")).get("name");
        // nickname이 아닌 사용자 이름
    }

    @Override
    public String getPassword() {
        return "naver";
    }
}
