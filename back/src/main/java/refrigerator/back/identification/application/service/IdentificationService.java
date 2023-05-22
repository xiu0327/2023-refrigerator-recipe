package refrigerator.back.identification.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.identification.application.domain.IdentificationMethod;
import refrigerator.back.identification.application.port.in.CheckNumberUseCase;
import refrigerator.back.identification.application.port.in.SendNumberUseCase;
import refrigerator.back.identification.application.port.out.IdentificationRedisPort;
import refrigerator.back.identification.exception.IdentificationExceptionType;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdentificationService implements SendNumberUseCase, CheckNumberUseCase {

    private final IdentificationMethod identificationMethod;
    private final IdentificationRedisPort identificationRedisPort;

    @Override
    public String sendAuthenticationNumber(String email, Long duration) {
        String code = createCode();
        identificationMethod.sendAuthenticationCode(email, code);
        identificationRedisPort.setData(code, email, duration);
        return code;
    }

    private String createCode(){
        String code = UUID.randomUUID().toString();
        return code.substring(0, 8);
    }

    @Override
    public Boolean checkAuthenticationNumber(String inputCode, String email) {
        String findEmail = identificationRedisPort.getData(inputCode);
        if(findEmail == null){
            throw new BusinessException(IdentificationExceptionType.TIME_OUT_CODE);
        }
        if (findEmail.equals(email)){
            identificationRedisPort.deleteData(inputCode);
            return true;
        }
        throw new BusinessException(IdentificationExceptionType.INCORRECT_CODE);
    }
}
