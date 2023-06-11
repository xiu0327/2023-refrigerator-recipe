package refrigerator.back.recipe_search.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe_search.adapter.out.dto.OutRecipeDto;
import refrigerator.back.recipe_search.adapter.out.repository.RecipeSearchSelectQueryRepository;
import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;
import refrigerator.back.recipe_search.application.domain.RecipeSearchDataCollection;
import refrigerator.back.recipe_search.application.port.out.GetRecipeSearchDataPort;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecipeSearchAdapter implements GetRecipeSearchDataPort {

    private final RecipeSearchSelectQueryRepository repository;


    @Override
    public RecipeSearchDataCollection search(RecipeSearchCondition condition, int page, int size) {
        List<OutRecipeDto> data = repository.searchRecipe(condition, PageRequest.of(page, size));
        return new RecipeSearchDataCollection(data);
    }

    @Override
    public RecipeSearchDataCollection normalSearch(int page, int size) {
        List<OutRecipeDto> data = repository.normalSearchRecipe(page, size);
        return new RecipeSearchDataCollection(data);
    }

}
