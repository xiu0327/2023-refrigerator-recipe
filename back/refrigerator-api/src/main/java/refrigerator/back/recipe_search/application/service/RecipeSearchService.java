package refrigerator.back.recipe_search.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe_search.adapter.RecipeSearchDataMapper;
import refrigerator.back.recipe_search.adapter.in.dto.InRecipeSearchDto;
import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;
import refrigerator.back.recipe_search.application.port.in.SearchRecipeUseCase;
import refrigerator.back.recipe_search.application.port.out.GetRecipeSearchDataPort;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeSearchService implements SearchRecipeUseCase {

    private final GetRecipeSearchDataPort port;
    private final RecipeSearchDataMapper mapper;

    @Override
    public List<InRecipeSearchDto> search(RecipeSearchCondition condition, int page, int size) {
        condition.parameterCheck();
        return port.search(condition, page, size).mappingToInRecipeSearchDto(mapper);
    }

    @Override
    public List<InRecipeSearchDto> normalSearch(int page, int size) {
        return port.normalSearch(page, size).mappingToInRecipeSearchDto(mapper);
    }

}
