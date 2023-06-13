package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.application.domain.dto.RecipeDomainDto;

public interface GetRecipeBasicsDataPort {
    RecipeDomainDto getData(Long recipeId);
}
