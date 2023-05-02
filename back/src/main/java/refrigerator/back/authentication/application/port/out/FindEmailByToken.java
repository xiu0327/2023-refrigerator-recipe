package refrigerator.back.authentication.application.port.out;

import refrigerator.back.authentication.application.domain.TokenInfoDTO;

public interface FindEmailByToken {
    TokenInfoDTO findEmailByToken(String token);
}
