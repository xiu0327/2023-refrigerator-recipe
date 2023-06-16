package refrigerator.back.recipe_searchword.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import refrigerator.back.global.exception.domain.BasicExceptionType;

@AllArgsConstructor
@Getter
public enum SearchWordExceptionType implements BasicExceptionType {
    EMPTY_WORD_LIST("EMPTY_WORD_LIST", "검색어를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST)
    ;

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public String getErrorCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return null;
    }
}
