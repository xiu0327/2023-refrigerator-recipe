package refrigerator.back.recipe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.adapter.in.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe.application.domain.RecipeRecommend;
import refrigerator.back.recipe.application.port.in.RecommendRecipeUseCase;
import refrigerator.back.recipe.application.port.out.FindIngredientNamesPort;
import refrigerator.back.recipe.application.port.out.FindRecommendRecipeInfoPort;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeRecommendService implements RecommendRecipeUseCase {

    private final FindRecommendRecipeInfoPort findRecipePort;
    private final FindIngredientNamesPort findIngredientPort;

    @Override
    @Transactional(readOnly = true)
    public List<InRecipeRecommendDTO> recommend(String memberId) {
        Map<Long, RecipeRecommend> recipes = findRecipePort.findRecipeIngredientNames();
        Set<String> myIngredientNames = findIngredientPort.findIngredientNames(memberId);
        if (myIngredientNames.size() <= 0){
            throw new BusinessException(RecipeExceptionType.EMPTY_MEMBER_INGREDIENT);
        }
        recipes.values().forEach(recipe -> recipe.count(myIngredientNames));
        List<Long> ids = recipes.keySet().stream()
                .sorted(Comparator.comparingDouble(
                        key -> recipes.get(key).getMatchPercent()).reversed())
                .limit(10)
                .collect(Collectors.toList());
        List<InRecipeRecommendDTO> result = findRecipePort.findRecipeByIds(ids);
        result.forEach(item -> item.setMatch(recipes.get(item.getRecipeID()).getMatchPercent()));
        return result;
    }

}
