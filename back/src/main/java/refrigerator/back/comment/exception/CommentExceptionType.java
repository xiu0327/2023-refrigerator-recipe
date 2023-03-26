package refrigerator.back.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import refrigerator.back.global.exception.BasicExceptionType;


@Getter
@AllArgsConstructor
public enum CommentExceptionType implements BasicExceptionType {
    NOT_FOUND_COMMENT("NOT_FOUND_COMMENT", "해당 댓글을 찾지 못했습니다.", HttpStatus.BAD_REQUEST)
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
