package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.adapter.in.dto.InRecipeIngredientVolumeDTO;

import java.util.List;

public interface FindRecipeIngredientVolumeListUseCase {
    List<InRecipeIngredientVolumeDTO> getRecipeIngredientVolumeList(Long recipeId);
}
