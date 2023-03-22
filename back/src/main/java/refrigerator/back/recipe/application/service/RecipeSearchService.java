package refrigerator.back.recipe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.adapter.in.dto.InRecipeBasicListDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.application.domain.entity.RecipeSearchCondition;
import refrigerator.back.recipe.application.port.in.FindSearchConditionUseCase;
import refrigerator.back.recipe.application.port.in.SearchRecipeUseCase;
import refrigerator.back.recipe.application.port.out.SearchRecipePort;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeSearchService implements SearchRecipeUseCase, FindSearchConditionUseCase {

    private final SearchRecipePort searchRecipePort;

    @Override
    public InRecipeBasicListDTO<InRecipeDTO> search(RecipeSearchCondition condition, int page, int size) {
        condition.parameterCheck();
        return new InRecipeBasicListDTO<>(
                searchRecipePort.search(condition, page, size));
    }

    @Override
    public InRecipeBasicListDTO<String> findRecipeFoodTypeCond() {
        return new InRecipeBasicListDTO<>(
                searchRecipePort.findRecipeFoodTypeCond());
    }

    @Override
    public InRecipeBasicListDTO<String> findRecipeCategoryCond() {
        return new InRecipeBasicListDTO<>(
                searchRecipePort.findRecipeCategoryCond());
    }
}
