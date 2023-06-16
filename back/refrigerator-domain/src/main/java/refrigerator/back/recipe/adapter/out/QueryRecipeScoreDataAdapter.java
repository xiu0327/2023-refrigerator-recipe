package refrigerator.back.recipe.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.adapter.out.repository.RecipePersistenceRepository;
import refrigerator.back.recipe.application.domain.entity.RecipeScore;
import refrigerator.back.recipe.application.port.out.GetRecipeScoreDataPort;
import refrigerator.back.recipe.exception.RecipeExceptionType;

@Repository
@RequiredArgsConstructor
public class QueryRecipeScoreDataAdapter implements GetRecipeScoreDataPort {

    private final RecipePersistenceRepository repository;

    @Override
    public RecipeScore findOne(Long recipeId) {
        return repository.findRecipeScoreByRecipeId(recipeId)
                .orElseThrow(() -> new BusinessException(RecipeExceptionType.NOT_FOUND_RECIPE_SCORE));
    }
}
