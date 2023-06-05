package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.application.domain.RecipeRecommend;

public interface FindRecommendRecipeInfoPort {
    RecipeRecommend getRecipeRecommend(String memberId);
}
