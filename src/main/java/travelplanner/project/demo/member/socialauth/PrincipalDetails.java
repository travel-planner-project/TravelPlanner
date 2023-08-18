package travelplanner.project.demo.member.socialauth;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import travelplanner.project.demo.member.Member;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Data
public class PrincipalDetails implements OAuth2User {
    private Member user;
    private Map<String, Object> attributes;


    //OAuth 로그인 생성자
    public PrincipalDetails(Member user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;

    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER"));
    }

    @Override
    public String getName() {
        return  (String) ((Map) attributes.get("properties")).get("nickname");
    }
}
