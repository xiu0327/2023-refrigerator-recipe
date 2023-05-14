package refrigerator.back.authentication.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.authentication.application.port.in.LogoutUseCase;
import refrigerator.back.authentication.application.port.out.*;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutUseCase {

    private final AddBlackListTokenPort addBlackListTokenPort;
    private final FindDurationByTokenPort findDurationByToken;
    private final FindEmailByTokenPort findEmailByToken;
    private final ValidateTokenPort validateTokenPort;
    private final RemoveRefreshTokenPort removeRefreshTokenPort;

    @Override
    public void logout(String accessToken) {
        if (!validateTokenPort.isExpired(accessToken)){
            long duration = calculationExpirationTime(findDurationByToken.findDuration(accessToken));
            addBlackListTokenPort.addToken(accessToken, duration);
        }
        String email = findEmailByToken.findEmailByToken(accessToken).getEmail();
        removeRefreshTokenPort.removeRefreshToken(email);
    }

    private long calculationExpirationTime(Date time){
        return time.getTime() - new Date().getTime();
    }

}
