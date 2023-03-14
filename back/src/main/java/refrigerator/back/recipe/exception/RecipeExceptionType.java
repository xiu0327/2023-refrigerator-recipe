package refrigerator.back.recipe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import refrigerator.back.global.exception.BasicExceptionType;

@AllArgsConstructor
@Getter
public enum RecipeExceptionType implements BasicExceptionType {
    NOT_FOUND_RECIPE("NOT_FOUND_RECIPE", "레시피 정보를 가져올 수 없습니다.", HttpStatus.BAD_REQUEST),
    WRONG_DIFFICULTY("WRONG_DIFFICULTY", "잘못된 난이도 입니다.", HttpStatus.NOT_FOUND),
    WRONG_INGREDIENT_TYPE("WRONG_INGREDIENT_TYPE", "잘못된 재료 타입입니다.", HttpStatus.NOT_FOUND)
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
