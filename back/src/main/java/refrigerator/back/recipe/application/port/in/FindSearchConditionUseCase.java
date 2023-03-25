package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.adapter.in.dto.InRecipeBasicListDTO;

public interface FindSearchConditionUseCase {
    InRecipeBasicListDTO<String> findRecipeFoodTypeCond();
    InRecipeBasicListDTO<String> findRecipeCategoryCond();
}
