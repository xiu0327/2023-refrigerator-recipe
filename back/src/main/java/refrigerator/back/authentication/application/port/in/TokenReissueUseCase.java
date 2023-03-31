package refrigerator.back.authentication.application.port.in;

import refrigerator.back.authentication.adapter.in.dto.TokenDTO;

public interface TokenReissueUseCase {
    TokenDTO reissue(String accessToken);
}
