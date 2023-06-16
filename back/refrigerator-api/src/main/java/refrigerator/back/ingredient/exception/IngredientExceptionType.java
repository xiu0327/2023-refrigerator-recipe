package refrigerator.back.ingredient.exception;

import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import refrigerator.back.global.exception.domain.BasicExceptionType;
import refrigerator.back.global.exception.domain.BasicHttpStatus;
import refrigerator.back.global.exception.domain.BusinessException;

@AllArgsConstructor
public enum IngredientExceptionType implements BasicExceptionType {

    NOT_FOUND_INGREDIENT("NOT_FOUND_INGREDIENT", "해당 식재료를 찾을 수 없습니다.", BasicHttpStatus.NOT_FOUND),
    EMPTY_INGREDIENT_LIST("EMPTY_INGREDIENT_LIST", "등록된 식재료가 없습니다.", BasicHttpStatus.BAD_REQUEST),
    CHECK_INGREDIENT_STORAGE_METHOD("CHECK_INGREDIENT_STORAGE_METHOD", "보관 방법을 확인해 주세요.", BasicHttpStatus.BAD_REQUEST),
    NOT_FOUND_REGISTERED_INGREDIENT("NOT_FOUND_REGISTERED_INGREDIENT", "등록되어 있지 않은 식재료입니다.", BasicHttpStatus.NOT_FOUND),
    NOT_VALID_REQUEST_BODY("NOT_VALID_REQUEST_BODY", "요청하신 데이터가 유효하지 않습니다.", BasicHttpStatus.BAD_REQUEST),
    EXCEEDED_CAPACITY_RANGE("EXCEEDED_CAPACITY_RANGE", "용량 범위를 초과하였습니다.", BasicHttpStatus.BAD_REQUEST),
    TEST_ERROR("TEST_ERROR", "테스트 오류", BasicHttpStatus.BAD_REQUEST),
    EXCEEDED_EXPIRATION_DATE("EXCEEDED_EXPIRATION_DATE", "식재료의 유통기한이 초과되었습니다.", BasicHttpStatus.BAD_REQUEST);

    private String errorCode;
    private String message;
    private BasicHttpStatus httpStatus;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public BasicHttpStatus getHttpStatus() {
        return httpStatus;
    }
}
