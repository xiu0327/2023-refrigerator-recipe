package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.adapter.in.dto.InRecipeIngredientVolumeDTO;

import java.util.List;

public interface FindRecipeIngredientVolumePort {
    List<InRecipeIngredientVolumeDTO> getRecipeIngredientVolumes(Long recipeId);
}
