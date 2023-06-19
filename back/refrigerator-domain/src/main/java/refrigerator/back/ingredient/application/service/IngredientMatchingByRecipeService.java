package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;
import refrigerator.back.ingredient.application.port.in.matchByRecipe.MatchIngredientByRecipeUseCase;
import refrigerator.back.ingredient.application.port.out.recipeIngredient.FindRecipeIngredientPort;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientListPort;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientMatchingByRecipeService implements MatchIngredientByRecipeUseCase {

    private final FindRecipeIngredientPort findRecipeIngredientPort;
    private final FindIngredientListPort findIngredientListPort;

    @Override
    public List<Long> getIngredientIds(String memberId, Long recipeId) {

        Map<String, Boolean> nameMap = findIngredientListPort.getIngredientListOfAll(memberId)
                .stream().collect(Collectors.toMap(IngredientDTO::getName, ingredientDTO -> true));

        return findRecipeIngredientPort.getRecipeIngredient(recipeId).stream()
                .filter(item -> nameMap.getOrDefault(item.getName(), false))
                .map(RecipeIngredientDto::getIngredientId)
                .collect(Collectors.toList());
    }
}
