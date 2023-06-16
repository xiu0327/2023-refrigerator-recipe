package refrigerator.back.global.exception.domain;

import org.springframework.http.HttpStatus;

public interface BasicExceptionType {
    String getErrorCode();
    String getMessage();
    BasicHttpStatus getHttpStatus();
}
