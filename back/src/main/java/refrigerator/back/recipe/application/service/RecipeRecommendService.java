package refrigerator.back.recipe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.adapter.in.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe.application.port.in.RecommendRecipeUseCase;
import refrigerator.back.recipe.application.port.out.FindIngredientNameListByMemberPort;
import refrigerator.back.recipe.application.port.out.FindRecommendRecipeInfoPort;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeRecommendService implements RecommendRecipeUseCase {

    private final FindRecommendRecipeInfoPort findRecommendRecipeInfoPort;
    private final FindIngredientNameListByMemberPort findIngredientNamePort;

    @Override
    public List<InRecipeRecommendDTO> recommend(String memberId) {
        Map<Long, Set<String>> ingredient = findRecommendRecipeInfoPort.getRecipeIngredientNameList();
        List<String> ingredientNameListByMember = findIngredientNamePort.findIngredientNameListByMember(memberId);
        if (ingredientNameListByMember.size() <= 0){
            throw new BusinessException(RecipeExceptionType.EMPTY_MEMBER_INGREDIENT);
        }
        Map<Long, Double> matchPercent = calculationMatchPercent(ingredient, ingredientNameListByMember);
        List<InRecipeRecommendDTO> recommendRecipeInfo =
                findRecommendRecipeInfoPort.findRecommendRecipeInfo(getRecommendRecipeIds(matchPercent));
        recommendRecipeInfo.forEach(dto -> dto.setMatch(matchPercent.get(dto.getRecipeId())));
        recommendRecipeInfo.sort((value1, value2) -> value2.getMatch().compareTo(value1.getMatch()));
        return recommendRecipeInfo;
    }

    public List<Long> getRecommendRecipeIds(Map<Long, Double> matchPercent) {
        List<Long> matchPercentKeyList = new ArrayList<>(matchPercent.keySet());
        matchPercentKeyList.sort((value1, value2) -> matchPercent.get(value2).compareTo(matchPercent.get(value1)));
        List<Long> recipeIds = new ArrayList<>();
        for (int i = 0 ; i < 10; i++){
            recipeIds.add(matchPercentKeyList.get(i));
        }
        return recipeIds;
    }

    public Map<Long, Double> calculationMatchPercent(Map<Long, Set<String>> ingredient, List<String> ingredientNameListByMember) {
        Map<Long, Double> matchPercentMaps = new HashMap<>();
        for (Long key : ingredient.keySet()) {
            int count = 0;
            for (String memberIngredient : ingredientNameListByMember) {
                if (ingredient.get(key).contains(memberIngredient)){
                    count++;
                }
            }
            double matchPercent = (count / (double) ingredient.get(key).size()) * 100;
            if (matchPercent > 0.0){
                matchPercentMaps.put(key, matchPercent);
            }
        }
        return matchPercentMaps;
    }
}
