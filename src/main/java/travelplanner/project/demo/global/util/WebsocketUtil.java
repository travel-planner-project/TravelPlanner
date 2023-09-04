package travelplanner.project.demo.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;


import java.util.Date;

@Component
public class WebsocketUtil extends StompSessionHandlerAdapter {

    private static final String SECRET_KEY = "68a4ef27a3f2f0f605a6781e6be34b466b5da3d11db5384218c407e99e6dcecf3361e1f6def13c78f2deb1e6e822bef2ca1c95b1166c97c5278ad81fdba4538";

    public boolean isValidToken(String token){
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            return claims.getBody()
                    .getExpiration()
                    .after(new Date());

        } catch (ExpiredJwtException e) {
            throw  e;
        } catch (Exception e) {
            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
        }
    }

    public String getEmail (String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
