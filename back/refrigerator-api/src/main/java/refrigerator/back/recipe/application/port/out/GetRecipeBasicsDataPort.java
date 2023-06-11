package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.adapter.out.dto.OutRecipeDto;

public interface GetRecipeBasicsDataPort {
    OutRecipeDto getData(Long recipeId);
}
