package refrigerator.back.identification.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import refrigerator.back.global.exception.BasicExceptionType;

@AllArgsConstructor
public enum IdentificationExceptionType implements BasicExceptionType {
    FAIL_SEND_EMAIL("FAIL_SEND_EMAIL", "이메일을 전송하지 못했습니다.", HttpStatus.BAD_REQUEST),
    NOT_EQUAL_CODE("NOT_EQUAL_CODE", "인증 코드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    TIME_OUT_CODE("TIME_OUT_CODE", "유효 시간이 만료되었습니다.", HttpStatus.BAD_REQUEST)
    ;


    private String errorCode;
    private String message;
    private HttpStatus httpStatus;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
