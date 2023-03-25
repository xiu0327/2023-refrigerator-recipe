package refrigerator.back.ingredient.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import refrigerator.back.global.exception.BasicExceptionType;

@AllArgsConstructor
public enum IngredientExceptionType implements BasicExceptionType {

    TEST_ERROR("TEST_ERROR", "테스트 에러", HttpStatus.NOT_FOUND);

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
