package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.application.dto.RecipeDomainDto;

public interface FindRecipeDtoPort {
    RecipeDomainDto getData(Long recipeId);
}
