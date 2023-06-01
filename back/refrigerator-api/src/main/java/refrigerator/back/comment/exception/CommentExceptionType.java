package refrigerator.back.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import refrigerator.back.global.exception.BasicExceptionType;


@Getter
@AllArgsConstructor
public enum CommentExceptionType implements BasicExceptionType {
    NOT_FOUND_COMMENT("NOT_FOUND_COMMENT", "해당 댓글을 찾지 못했습니다.", HttpStatus.BAD_REQUEST),
    NO_EDIT_RIGHTS("NO_EDIT_RIGHTS", "수정 권한이 없습니다.", HttpStatus.BAD_REQUEST),
    DELETE_COMMENT("DELETE_COMMENT", "이미 삭제된 댓글입니다.", HttpStatus.BAD_REQUEST),
    DUPLICATE_HEART_REQUEST("DUPLICATE_HEART_REQUEST", "이미 좋아요가 눌러진 상태이거나 취소된 상태입니다.", HttpStatus.BAD_REQUEST)
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
