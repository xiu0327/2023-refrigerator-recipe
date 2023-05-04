package refrigerator.back.global.exception;

import org.springframework.validation.BindingResult;

public class ValidationExceptionHandler {
    public static void check(BindingResult result, BasicExceptionType exceptionType) {
        if (result.hasErrors()){
            throw new BusinessException(exceptionType);
        }
    }
}
