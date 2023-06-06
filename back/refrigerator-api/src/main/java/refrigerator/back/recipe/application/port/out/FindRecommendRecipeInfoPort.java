package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.application.domain.RecipeRecommendDomainService;

public interface FindRecommendRecipeInfoPort {
    RecipeRecommendDomainService getRecipeRecommend(String memberId);
}
