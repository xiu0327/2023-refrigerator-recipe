package refrigerator.back.recipe_search.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.recipe.application.domain.value.RecipeDifficulty;
import refrigerator.back.recipe.application.domain.value.RecipeType;
import refrigerator.back.recipe_search.application.port.in.FindSearchConditionUseCase;
import refrigerator.back.recipe_search.application.port.out.GetSearchConditionDataPort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeSearchConditionService implements FindSearchConditionUseCase {

    private final GetSearchConditionDataPort port;

    @Override
    public List<String> findRecipeFoodTypeCond() {
        return port.findRecipeFoodTypeCond();
    }

    @Override
    public List<String> findRecipeCategoryCond() {
        return port.findRecipeCategoryCond();
    }

    @Override
    public List<String> findRecipeTypeCond() {
        return Arrays.stream(RecipeType.values())
                .map(RecipeType::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findRecipeDifficultyCond() {
        return Arrays.stream(RecipeDifficulty.values())
                .map(RecipeDifficulty::getLevelName)
                .collect(Collectors.toList());
    }
}
