package refrigerator.back.authentication.application.port.in;

import refrigerator.back.authentication.application.dto.TokenDTO;

public interface LoginUseCase {
    TokenDTO login(String email, String password);
}
