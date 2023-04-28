package refrigerator.back.ingredient.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import refrigerator.back.global.exception.BasicExceptionType;

@AllArgsConstructor
public enum IngredientExceptionType implements BasicExceptionType {

    NOT_FOUND_INGREDIENT("NOT_FOUND_INGREDIENT", "해당 식재료를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    EMPTY_INGREDIENT_LIST("EMPTY_INGREDIENT_LIST", "등록된 식재료가 없습니다.", HttpStatus.BAD_REQUEST),
    CHECK_INGREDIENT_STORAGE_METHOD("CHECK_INGREDIENT_STORAGE_METHOD", "보관 방법을 확인해 주세요.", HttpStatus.BAD_REQUEST)
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
