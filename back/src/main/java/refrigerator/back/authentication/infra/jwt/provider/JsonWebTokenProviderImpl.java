package refrigerator.back.authentication.infra.jwt.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import refrigerator.back.authentication.infra.jwt.TokenStatus;

import java.security.Key;
import java.util.Date;

import static org.springframework.util.StringUtils.hasText;
import static refrigerator.back.authentication.infra.jwt.JsonWebTokenKey.*;

@Component
public class JsonWebTokenProviderImpl implements JsonWebTokenProvider {

    private final Key key;

    public JsonWebTokenProviderImpl(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secretKey));
    }

    @Override
    public String createToken(String username, String authority, long expirationTime) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(AUTHORITIES_KEY, authority);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 토큰 발행 유저 정보
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + expirationTime)) // 토큰 만료 시간
                .signWith(key, SignatureAlgorithm.HS512) // 키와 알고리즘 설정
                .compact();
    }

    @Override
    public Claims parseClaims(String token) {
        try{
            return Jwts.parserBuilder().setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch(ExpiredJwtException e){ // 만료된 토큰
            return e.getClaims();
        }
    }

    @Override
    public TokenStatus validateToken(String token) {
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return TokenStatus.PASS;
        } catch (ExpiredJwtException e){
            return TokenStatus.EXPIRED;
        } catch (Exception e){
            return TokenStatus.WRONG;
        }
    }

}
