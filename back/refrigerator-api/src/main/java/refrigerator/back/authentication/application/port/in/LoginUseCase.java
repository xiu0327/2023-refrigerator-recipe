package refrigerator.back.authentication.application.port.in;

import refrigerator.back.authentication.adapter.in.dto.TokenDTO;

public interface LoginUseCase {
    TokenDTO login(String email, String password);
}
