package server.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;


@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    // JWT 생성 및 유효성을 검증
    // Claim정보에는 토큰에부가적으로 실어 보낼 정보를 담을 수 있음.
    private String secretKey  = "tkskdlrnjsdydrn"; // 사나이권용구

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    //토큰 유효시간 : 30분
    private long tokenValidTime = 30 * 60 * 1000L;
    private final UserDetailsService userDetailsService;

    //객체 초기화, secretKey Base64로 인코딩
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey .getBytes());
    }

    //JWT Token 생성
    public String createToken(String userPK){
        Claims claims = Jwts.claims().setSubject(userPK); // JWT payload 에 저장되는 정보단위, 정보는 key / value 쌍으로 저장됨
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(key)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .compact();

    }

    //JWT 토큰에서 인증정보조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰에서 회원 정보 추출
    public String getUserPk(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옴 ( X - AUTH - TOKEN : Token값
    public String resolveToken(HttpServletRequest request){
        return  request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return  !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e){
            return false;
        }

    }
}
