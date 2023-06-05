package refrigerator.back.recipe.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.in.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe.adapter.mapper.RecipeDtoMapper;
import refrigerator.back.recipe.adapter.out.repository.query.RecipeRecommendQueryRepository;
import refrigerator.back.recipe.application.port.out.FindMyIngredientNamesPort;
import refrigerator.back.recipe.application.port.out.FindRecipeRecommendInfoPort;


import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecipeRecommendAdapter implements FindMyIngredientNamesPort, FindRecipeRecommendInfoPort {

    private final RecipeRecommendQueryRepository repository;
    private final RecipeDtoMapper mapper;

    @Override
    public Map<Long, Set<String>> findRecipeIngredientNames() {
        return repository.findRecipeIngredientNames();

    }

    @Override
    public Set<String> findMyIngredientNames(String memberId) {
        return new HashSet<>(repository.findIngredientName(memberId));
    }

    @Override
    public List<InRecipeRecommendDTO> findInfoByIds(List<Long> ids) {
        return repository.findRecommendRecipes(ids).stream()
                .map(dto -> mapper.toInRecipeRecommendDto(
                        dto,
                        dto.getScore().calculateScore(),
                        0.0))
                .collect(Collectors.toList());
    }
}
