package refrigerator.back.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static refrigerator.back.global.exception.BasicExceptionFormat.*;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BasicExceptionFormat> businessException(BusinessException e){
        return new ResponseEntity<>(create(e.getBasicExceptionType()), e.getBasicExceptionType().getHttpStatus());
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<BasicExceptionFormat> oauth2AuthenticationException(OAuth2AuthenticationException e){
        return new ResponseEntity<>(new BasicExceptionFormat("FAIL_OAUTH2_LOGIN", "간편 로그인에 실패하였습니다."), HttpStatus.BAD_REQUEST);
    }

}
