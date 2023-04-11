package refrigerator.back.mybookmark.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import refrigerator.back.global.exception.BasicExceptionType;

@Getter
@AllArgsConstructor
public enum MyBookmarkExceptionType implements BasicExceptionType {
    NOT_FOUND_BOOKMARK("NOT_FOUND_BOOKMARK", "북마크를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ALREADY_ADD_BOOKMARK("ALREADY_ADD_BOOKMARK", "이미 추가된 북마크입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_DELETE_BOOKMARK("ALREADY_DELETE_BOOKMARK", "이미 삭제된 북마크입니다.", HttpStatus.BAD_REQUEST),
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
