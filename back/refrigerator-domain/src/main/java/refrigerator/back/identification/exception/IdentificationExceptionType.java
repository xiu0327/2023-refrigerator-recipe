package refrigerator.back.identification.exception;

import lombok.AllArgsConstructor;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BasicHttpStatus;

import static refrigerator.back.global.exception.BasicHttpStatus.*;

@AllArgsConstructor
public enum IdentificationExceptionType implements BasicExceptionType {
    FAIL_SEND_EMAIL("FAIL_SEND_EMAIL", "이메일을 전송하지 못했습니다.", BAD_REQUEST),
    INCORRECT_CODE("INCORRECT_CODE", "인증 코드가 일치하지 않습니다.", BAD_REQUEST),
    TIME_OUT_CODE("TIME_OUT_CODE", "유효 시간이 만료되었습니다.", BAD_REQUEST),
    NOT_VALID_REQUEST_BODY("NOT_VALID_REQUEST_BODY", "입력하신 데이터가 유효하지 않습니다.", BAD_REQUEST),
    ;



    private String errorCode;
    private String message;
    private BasicHttpStatus httpStatus;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public BasicHttpStatus getHttpStatus() {
        return httpStatus;
    }
}
