package refrigerator.back.authentication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import refrigerator.back.global.exception.BasicExceptionType;

@AllArgsConstructor
@Getter
public enum AuthenticationExceptionType implements BasicExceptionType {
    NOT_FOUND_AUTHORITY("NOT_FOUND_AUTHORITY", "권한이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_EQUAL_PASSWORD("NOT_EQUAL_PASSWORD", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    ALREADY_LOGIN_MEMBER("WRONG_REQUEST", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_LOGOUT_MEMBER("ALREADY_LOGOUT_MEMBER", "이미 로그아웃된 사용자 입니다.", HttpStatus.BAD_REQUEST)
    ;

    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
