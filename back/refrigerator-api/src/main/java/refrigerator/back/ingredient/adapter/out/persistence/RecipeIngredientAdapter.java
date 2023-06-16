package refrigerator.back.ingredient.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.ingredient.adapter.out.mapper.OutRecipeIngredientMapper;
import refrigerator.back.ingredient.adapter.out.repository.query.RecipeIngredientQueryRepository;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;
import refrigerator.back.ingredient.application.port.out.FindRecipeIngredientPort;


import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecipeIngredientAdapter implements FindRecipeIngredientPort {

    private final RecipeIngredientQueryRepository recipeIngredientQueryRepository;
    private final OutRecipeIngredientMapper mapper;

    @Override
    public List<RecipeIngredientDto> getRecipeIngredient(Long recipeId) {
        return recipeIngredientQueryRepository
                .getRecipeIngredientList(recipeId)
                .stream().map(mapper::toRecipeIngredientDto)
                .collect(Collectors.toList());
    }
}
