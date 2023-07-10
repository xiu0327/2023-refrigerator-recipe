package refrigerator.back.recipe.outbound.adpater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.recipe.outbound.mapper.OutRecipeBasicDataMapper;
import refrigerator.back.recipe.outbound.repository.query.RecipeSelectQueryRepository;
import refrigerator.back.recipe.application.dto.RecipeDomainDto;
import refrigerator.back.recipe.application.port.out.CheckMemberBookmarkedStatusPort;
import refrigerator.back.recipe.application.port.out.FindRecipeDtoPort;

@Component
@RequiredArgsConstructor
public class QueryRecipeBasicDataAdapter implements FindRecipeDtoPort, CheckMemberBookmarkedStatusPort {

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
