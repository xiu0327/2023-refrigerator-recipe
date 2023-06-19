package refrigerator.back.ingredient.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.ingredient.adapter.mapper.OutRecipeIngredientMapper;
import refrigerator.back.ingredient.adapter.repository.IngredientQuerySubRepository;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;
import refrigerator.back.ingredient.application.port.out.recipeIngredient.FindRecipeIngredientPort;


import java.util.List;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class RecipeIngredientAdapter implements FindRecipeIngredientPort {

    private final IngredientQuerySubRepository ingredientSubQueryRepository;
    private final OutRecipeIngredientMapper mapper;

    @Override
    public List<RecipeIngredientDto> getRecipeIngredient(Long recipeId) {
        return ingredientSubQueryRepository.getRecipeIngredient(recipeId)
                .stream()
                .map(mapper::toRecipeIngredientDto)
                .collect(Collectors.toList());
    }
}
