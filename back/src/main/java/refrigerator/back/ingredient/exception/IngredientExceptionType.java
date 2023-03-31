package refrigerator.back.ingredient.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import refrigerator.back.global.exception.BasicExceptionType;

@AllArgsConstructor
public enum IngredientExceptionType implements BasicExceptionType {

    NOT_FOUND_INGREDIENT("NOT_FOUND_INGREDIENT", "해당 식재료를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

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
