package refrigerator.back.myscore.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import refrigerator.back.global.exception.domain.BasicExceptionType;

@AllArgsConstructor
public enum MyRecipeScoreExceptionType implements BasicExceptionType {

    NOT_FOUND_SCORE("NOT_FOUND_SCORE", "해당 레시피 점수를 가져오지 못했습니다.", HttpStatus.NOT_FOUND),
    WRONG_SCORE_SCOPE("WRONG_SCORE_SCOPE", "허용하지 않는 별점의 범위입니다.", HttpStatus.BAD_REQUEST)
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
