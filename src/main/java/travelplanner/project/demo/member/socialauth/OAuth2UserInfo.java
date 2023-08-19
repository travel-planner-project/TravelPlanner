package travelplanner.project.demo.member.socialauth;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getProfile();
    String getEmail();
    String getName();

    String getPassword();
}
