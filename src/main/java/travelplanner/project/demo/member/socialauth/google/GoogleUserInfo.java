package travelplanner.project.demo.member.socialauth.google;

import lombok.AllArgsConstructor;
import travelplanner.project.demo.member.socialauth.OAuth2UserInfo;

import java.util.Map;

@AllArgsConstructor
public class GoogleUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProfile() {
        return null;
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    @Override
    public String getPassword() {
        return null;
    }
}
