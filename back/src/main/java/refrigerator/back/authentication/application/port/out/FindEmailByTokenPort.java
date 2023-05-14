package refrigerator.back.authentication.application.port.out;

import refrigerator.back.authentication.application.domain.TokenInfoDTO;

public interface FindEmailByTokenPort {
    TokenInfoDTO findEmailByToken(String token);
}
