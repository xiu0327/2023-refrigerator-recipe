package refrigerator.back.recipe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.application.domain.entity.RecipeSearchCondition;
import refrigerator.back.recipe.application.domain.value.RecipeDifficulty;
import refrigerator.back.recipe.application.domain.value.RecipeType;
import refrigerator.back.recipe.application.port.in.FindSearchConditionUseCase;
import refrigerator.back.recipe.application.port.in.SearchRecipeUseCase;
import refrigerator.back.recipe.application.port.out.SearchRecipePort;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeSearchService implements SearchRecipeUseCase, FindSearchConditionUseCase {

    private final SearchRecipePort searchRecipePort;

    @Override
    public List<InRecipeDTO> search(RecipeSearchCondition condition, int page, int size) {
        condition.parameterCheck();
        return searchRecipePort.search(condition, page, size);
    }

    @Override
    public List<String> findRecipeFoodTypeCond() {
        return searchRecipePort.findRecipeFoodTypeCond();
    }

    @Override
    public List<String> findRecipeCategoryCond() {
        return searchRecipePort.findRecipeCategoryCond();
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
