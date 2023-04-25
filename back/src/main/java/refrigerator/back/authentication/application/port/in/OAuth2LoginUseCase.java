package refrigerator.back.authentication.application.port.in;

import refrigerator.back.authentication.adapter.in.dto.TokenDTO;

public interface OAuth2LoginUseCase {
    TokenDTO createToken(String email);
}
