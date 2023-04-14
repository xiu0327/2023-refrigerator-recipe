package refrigerator.back.recipe.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.in.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe.adapter.mapper.RecipeDtoMapper;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeRecommendDTO;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeRecommendIngredientDTO;
import refrigerator.back.recipe.adapter.out.repository.query.RecipeQueryRepository;
import refrigerator.back.recipe.adapter.out.repository.query.RecipeRecommendQueryRepository;
import refrigerator.back.recipe.application.port.out.FindIngredientNameListByMemberPort;
import refrigerator.back.recipe.application.port.out.FindRecommendRecipeInfoPort;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecipeRecommendAdapter implements FindRecommendRecipeInfoPort, FindIngredientNameListByMemberPort {

    private final RecipeRecommendQueryRepository recipeQueryRepository;
    private final RecipeDtoMapper mapper;

    @Override
    public Map<Long, Set<String>> getRecipeIngredientNameList() {
        List<OutRecipeRecommendIngredientDTO> ingredientList = recipeQueryRepository.findRecipeNameAndIngredientList();
        Map<Long, Set<String>> ingredientMaps = new HashMap<>();
        for (OutRecipeRecommendIngredientDTO ingredient : ingredientList) {
            if (!ingredientMaps.containsKey(ingredient.getRecipeId())){
                ingredientMaps.put(ingredient.getRecipeId(), new HashSet<>());
            }
            ingredientMaps.get(ingredient.getRecipeId()).add(ingredient.getIngredientName());
        }
        return ingredientMaps;
    }
    @Override
    public List<String> findIngredientNameListByMember(String memberId) {
        return recipeQueryRepository.findIngredientNameByMember(memberId);
    }

    @Override
    public List<InRecipeRecommendDTO> findRecommendRecipeInfo(List<Long> recipeIds) {
        return recipeQueryRepository.findRecommendRecipeListById(recipeIds).stream()
                .map(dto -> mapper.toInRecipeRecommendDto(dto, dto.getScore().calculateScore()))
                .collect(Collectors.toList());
    }
}
