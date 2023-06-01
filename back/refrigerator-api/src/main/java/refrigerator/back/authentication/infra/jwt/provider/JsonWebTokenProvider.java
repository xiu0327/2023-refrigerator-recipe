package refrigerator.back.authentication.infra.jwt.provider;
import io.jsonwebtoken.Claims;
import refrigerator.back.authentication.infra.jwt.TokenStatus;

public interface JsonWebTokenProvider {
    String createToken(String username, String authority, long expirationTime);
    Claims parseClaims(String token);
    TokenStatus validateToken(String token);
}
