package refrigerator.back.ingredient.application.domain;

import lombok.*;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.exception.IngredientExceptionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientSearchCondition {

    IngredientStorageType storage;
    Boolean deadline;
    String email;
}
