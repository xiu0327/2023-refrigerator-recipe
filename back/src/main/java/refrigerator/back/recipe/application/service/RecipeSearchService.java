package refrigerator.back.recipe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;
import refrigerator.back.recipe.application.port.in.FindSearchConditionUseCase;
import refrigerator.back.recipe.application.port.in.SearchRecipeUseCase;
import refrigerator.back.recipe.application.port.out.SearchRecipePort;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeSearchService implements SearchRecipeUseCase, FindSearchConditionUseCase {

    private final SearchRecipePort searchRecipePort;

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDomain> search(String recipeType,
                                     String foodType,
                                     String difficulty,
                                     Double score,
                                     int page,
                                     int size) {
        if (score < 1 || score > 5){
            throw new BusinessException(RecipeExceptionType.NOT_ACCEPTABLE_RANGE);
        }
        return searchRecipePort.search(recipeType, foodType, difficulty, score, page, size)
                .stream().map(RecipeDomain::calculateScoreAvg)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findRecipeFoodTypeCond() {
        return searchRecipePort.findRecipeFoodTypeCond();
    }

    @Override
    public List<String> findRecipeCategoryCond() {
        return searchRecipePort.findRecipeCategoryCond();
    }
}
