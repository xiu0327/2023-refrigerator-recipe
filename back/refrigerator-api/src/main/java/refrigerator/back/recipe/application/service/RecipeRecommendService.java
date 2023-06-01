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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingDouble;

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
        Map<Long, Double> ids = toExtractRecommendRecipeIds(recipes);
        return findRecipePort.findRecipeByIds(ids).stream()
                .sorted(comparingDouble(InRecipeRecommendDTO::getMatch).reversed())
                .collect(Collectors.toList());
    }

    private Map<Long, Double> toExtractRecommendRecipeIds(Map<Long, RecipeRecommend> recipes) {
        Map<Long, Double> ids = new HashMap<>();
        recipes.keySet().stream()
                .sorted(comparingDouble(
                        key -> recipes.get(key).getMatchPercent()).reversed())
                .limit(10)
                .forEach(key -> ids.put(key, recipes.get(key).getMatchPercent()));
        return ids;
    }

}
