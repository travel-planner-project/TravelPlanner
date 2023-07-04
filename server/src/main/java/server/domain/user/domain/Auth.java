package server.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String email;

    private String accessToken;
    private String refreshToken;

    public void accessUpdate(String accessToken){
        this.accessToken = accessToken;
    }

    public void refreshUpdate(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
