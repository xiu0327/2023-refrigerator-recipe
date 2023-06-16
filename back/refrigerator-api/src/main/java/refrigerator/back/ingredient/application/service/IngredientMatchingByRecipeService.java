package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;
import refrigerator.back.ingredient.application.port.in.MatchIngredientByRecipeUseCase;
import refrigerator.back.ingredient.application.port.out.FindRecipeIngredientPort;
import refrigerator.back.ingredient.application.port.out.ReadIngredientPort;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientMatchingByRecipeService implements MatchIngredientByRecipeUseCase {

    private final FindRecipeIngredientPort findRecipeIngredientPort;
    private final ReadIngredientPort readIngredientPort;

    @Override
    public List<Long> getData(String memberId, Long recipeId) {

        Map<String, Boolean> nameMap = new HashMap<>();
        readIngredientPort.getIngredientListOfAll(memberId)
                .forEach(item -> nameMap.put(item.getName(), true));

        return findRecipeIngredientPort.getRecipeIngredient(recipeId).stream()
                .filter(item -> nameMap.getOrDefault(item.getName(), false))
                .map(RecipeIngredientDto::getIngredientId)
                .collect(Collectors.toList());
    }

}
