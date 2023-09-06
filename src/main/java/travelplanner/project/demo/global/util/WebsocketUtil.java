package travelplanner.project.demo.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;


import java.util.Date;

@Component
@RequiredArgsConstructor
public class WebsocketUtil extends StompSessionHandlerAdapter {

//
//    public boolean isValidToken(String token){
//        try {
//            Jws<Claims> claims = Jwts.parser()
//                    .setSigningKey(SECRET_KEY)
//                    .parseClaimsJws(token);
//
//            return claims.getBody()
//                    .getExpiration()
//                    .after(new Date());
//
//        } catch (ExpiredJwtException e) {
//            throw  e;
//        } catch (Exception e) {
//            throw new ApiException(ErrorType.USER_NOT_AUTHORIZED);
//        }
//    }
//
//    public String getEmail (String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody().getSubject();
//    }
}
