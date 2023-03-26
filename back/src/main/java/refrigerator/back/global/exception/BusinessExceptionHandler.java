package refrigerator.back.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import refrigerator.back.global.exception.BasicExceptionFormat;
import refrigerator.back.global.exception.BusinessException;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BasicExceptionFormat> businessException(BusinessException e){
        return new ResponseEntity<>(BasicExceptionFormat.create(e.getBasicExceptionType()), e.getBasicExceptionType().getHttpStatus());
    }
}
