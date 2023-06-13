package refrigerator.back.authentication.application.port;

import refrigerator.back.authentication.application.domain.TokenStatus;
import refrigerator.back.authentication.application.dto.ParseClaimsDto;

public interface JsonWebTokenProvider {
    String createToken(String username, String authority, long expirationTime);
    ParseClaimsDto parseClaims(String token);
    TokenStatus validateToken(String token);
}
