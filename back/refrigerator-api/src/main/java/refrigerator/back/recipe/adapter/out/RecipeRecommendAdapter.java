package refrigerator.back.recipe.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.in.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe.adapter.mapper.RecipeDtoMapper;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeIngredientNameDTO;
import refrigerator.back.recipe.adapter.out.repository.query.RecipeRecommendQueryRepository;
import refrigerator.back.recipe.application.domain.RecipeRecommend;
import refrigerator.back.recipe.application.port.out.FindIngredientNamesPort;
import refrigerator.back.recipe.application.port.out.FindRecommendRecipeInfoPort;


import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecipeRecommendAdapter implements FindRecommendRecipeInfoPort, FindIngredientNamesPort {

    private final RecipeRecommendQueryRepository repository;
    private final RecipeDtoMapper mapper;

    @Override
    public Map<Long, RecipeRecommend> findRecipeIngredientNames() {
        Map<Long, RecipeRecommend> result = new HashMap<>();
        for (OutRecipeIngredientNameDTO ingredient : repository.findRecipeIngredientNames()) {
            RecipeRecommend recommend = result.getOrDefault(
                    ingredient.getRecipeId(),
                    new RecipeRecommend());
            recommend.addNames(ingredient.getIngredientName());
            result.put(ingredient.getRecipeId(), recommend);
        }
        return result;
    }
    @Override
    public Set<String> findIngredientNames(String memberId) {
        return new HashSet<>(repository.findIngredientName(memberId));
    }

    @Override
    public List<InRecipeRecommendDTO> findRecipeByIds(Map<Long, Double> recipeIds) {
        List<Long> ids = new ArrayList<>(recipeIds.keySet());
        return repository.findRecommendRecipes(ids).stream()
                .map(dto -> mapper.toInRecipeRecommendDto(
                        dto,
                        dto.getScore().calculateScore(),
                        recipeIds.get(dto.getRecipeId())))
                .collect(Collectors.toList());
    }
}
