package refrigerator.server.api.global.exception;

//import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import refrigerator.back.global.exception.BusinessException;

import java.net.ConnectException;
import java.util.Objects;

import static refrigerator.server.api.global.exception.BasicExceptionFormat.*;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BasicExceptionFormat> businessException(BusinessException e){

        HttpStatus code = HttpStatus.resolve(e.getBasicExceptionType().getHttpStatus().getCode());

        if (code == null)
            return new ResponseEntity<>(new BasicExceptionFormat("UNKNOWN_ERROR", "알 수 없는 오류가 발생하였습니다."), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(create(e.getBasicExceptionType()), code);
    }

//    @ExceptionHandler(OAuth2AuthenticationException.class)
//    public ResponseEntity<BasicExceptionFormat> oauth2AuthenticationException(OAuth2AuthenticationException e){
//        return new ResponseEntity<>(new BasicExceptionFormat("FAIL_OAUTH2_LOGIN", "간편 로그인에 실패하였습니다."), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<BasicExceptionFormat> notDatabaseConnected(ConnectException e){
        return new ResponseEntity<>(new BasicExceptionFormat("NOT_CONNECTED_SERVER", "현재 서버의 연결이 원활하지 않습니다."), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(RedisException.class)
//    public ResponseEntity<BasicExceptionFormat> notFoundCache(RedisException e){
//        return new ResponseEntity<>(new BasicExceptionFormat("NOT_FOUND_CACHE", e.getMessage()), HttpStatus.BAD_REQUEST);
//    }

}
