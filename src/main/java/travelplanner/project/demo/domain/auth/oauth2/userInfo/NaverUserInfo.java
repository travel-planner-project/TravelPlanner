package travelplanner.project.demo.domain.auth.oauth2.userInfo;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    @Override
    public String getProviderId() {
        return (String) ((Map) attributes.get("response")).get("id");
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
        return (String) ((Map) attributes.get("response")).get("nickname");
    }

    @Override
    public String getPassword() {
        return "naver";
    }
}
