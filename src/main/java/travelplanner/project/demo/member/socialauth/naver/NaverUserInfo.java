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
        return (String) ((Map) attributes.get("properties")).get("profile_image");
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return "naver";
    }
}
