package travelplanner.project.demo.domain.auth.oauth2.userInfo;

public interface OAuth2UserInfo {
    String getProfile();
    String getEmail();
    String getName();
    String getPassword();
}
