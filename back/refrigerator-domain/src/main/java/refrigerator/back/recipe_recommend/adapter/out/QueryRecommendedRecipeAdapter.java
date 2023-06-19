package refrigerator.back.recipe_recommend.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import refrigerator.back.recipe_recommend.adapter.RecipeRecommendDataMapper;
import refrigerator.back.recipe_recommend.application.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe_recommend.adapter.out.dto.OutRecipeIngredientNameDTO;
import refrigerator.back.recipe_recommend.adapter.out.dto.OutRecipeRecommendDTO;
import refrigerator.back.recipe_recommend.adapter.out.repository.RecipeRecommendSelectQueryRepository;
import refrigerator.back.recipe_recommend.application.domain.RecipeIngredientTuple;
import refrigerator.back.recipe_recommend.application.port.out.GetMyIngredientNameDataPort;
import refrigerator.back.recipe_recommend.application.port.out.GetRecipeRecommendInfoDataPort;


import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class QueryRecommendedRecipeAdapter implements GetMyIngredientNameDataPort, GetRecipeRecommendInfoDataPort {

    private final RecipeRecommendSelectQueryRepository repository;
    private final RecipeRecommendDataMapper mapper;

    @Override
    public Map<Long, Set<RecipeIngredientTuple>> findRecipeIngredientNames() {
        // 1. DB 에서 레시피 식재료 명 데이터 가져오기
        List<OutRecipeIngredientNameDTO> data = repository.selectRecipeIngredientNames();
        // 2. 동일한 recipeId 별로 Map 자료구조 형성
        Map<Long, Set<RecipeIngredientTuple>> result = new HashMap<>();
        data.forEach(ingredient -> {
            Set<RecipeIngredientTuple> tuples = result.getOrDefault(
                    ingredient.getRecipeId(),
                    new HashSet<>());
            tuples.add(new RecipeIngredientTuple(
                    ingredient.getIngredientName(),
                    ingredient.getType()
            ));
            result.put(ingredient.getRecipeId(), tuples);
        });
        return result;
    }

    @Override
    public Set<String> findMyIngredientNames(String memberId) {
        return repository.selectMyIngredientNames(memberId);
    }

    @Override
    public List<InRecipeRecommendDTO> findInfoByIds(List<Long> ids) {
        List<OutRecipeRecommendDTO> data = repository.selectRecipeInfoByIds(ids);
        return data.stream()
                .map(dto -> mapper.toInRecipeRecommendDto(dto, 0.0))
                .collect(Collectors.toList());
    }
}
