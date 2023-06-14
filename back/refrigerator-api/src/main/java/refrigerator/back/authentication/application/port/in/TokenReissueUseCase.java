package refrigerator.back.authentication.application.port.in;

import refrigerator.back.authentication.application.dto.TokenDTO;

public interface TokenReissueUseCase {
    TokenDTO reissue(String refreshToken);
}
