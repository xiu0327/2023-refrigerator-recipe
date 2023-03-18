package refrigerator.back.recipe.application.port.in;

import java.util.List;

public interface FindSearchConditionUseCase {
    List<String> findRecipeFoodTypeCond();
    List<String> findRecipeCategoryCond();
}
