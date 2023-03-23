package refrigerator.back.ingredient.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import refrigerator.back.global.exception.BasicExceptionType;

@AllArgsConstructor
public enum IngredientExceptionType implements BasicExceptionType {

    // 예외 작성
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
    public HttpStatus getHttpStatus() {return httpStatus;}
}
