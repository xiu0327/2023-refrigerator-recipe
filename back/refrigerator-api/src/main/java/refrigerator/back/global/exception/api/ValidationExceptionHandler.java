package refrigerator.back.global.exception.api;

import org.springframework.validation.BindingResult;
import refrigerator.back.global.exception.domain.BasicExceptionType;
import refrigerator.back.global.exception.domain.BusinessException;

public class ValidationExceptionHandler {
    public static void check(BindingResult result, BasicExceptionType exceptionType) {
        if (result.hasErrors()){
            throw new BusinessException(exceptionType);
        }
    }
}
