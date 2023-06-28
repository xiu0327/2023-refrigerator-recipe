package refrigerator.back.recipe.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.mapper.OutRecipeBasicDataMapper;
import refrigerator.back.recipe.adapter.out.repository.RecipeSelectQueryRepository;
import refrigerator.back.recipe.application.domain.dto.RecipeDomainDto;
import refrigerator.back.recipe.application.port.out.CheckMemberBookmarkedStatusPort;
import refrigerator.back.recipe.application.port.out.GetRecipeBasicsDataPort;

@Component
@RequiredArgsConstructor
public class QueryRecipeBasicDataAdapter implements GetRecipeBasicsDataPort, CheckMemberBookmarkedStatusPort {

    private final RecipeSelectQueryRepository repository;
    private final OutRecipeBasicDataMapper mapper;

    @Override
    public RecipeDomainDto getData(Long recipeId) {
        return repository.selectRecipeBasics(recipeId).mappingToDomainDto(mapper);
    }

    @Override
    public Boolean getStatus(Long recipeId, String memberId) {
        Long result = repository.selectBookmarkByMemberId(recipeId, memberId);
        return result != null;
    }
}
