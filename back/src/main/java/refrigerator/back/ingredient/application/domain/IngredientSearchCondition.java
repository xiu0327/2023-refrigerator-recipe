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

    /*
    public static IngredientSearchCondition check(IngredientSearchCondition condition) {
        List<String> method = new ArrayList<>(Arrays.asList("냉장", "냉동", "실온", "조미료"));

        String storageMethod = condition.getStorage();

        boolean check = false;

        for (String s : method) {
            if(storageMethod.equals(s)){
                check = true;
            }
        }

        if(check == false)
            throw new BusinessException(IngredientExceptionType.CHECK_INGREDIENT_STORAGE_METHOD);

        return condition;

    }
    */
}
