package refrigerator.back.recipe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.recipe.adapter.in.dto.InRecipeIngredientVolumeDTO;
import refrigerator.back.recipe.application.port.in.FindRecipeIngredientVolumeListUseCase;
import refrigerator.back.recipe.application.port.out.FindRecipeIngredientVolumePort;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeCookingService implements FindRecipeIngredientVolumeListUseCase {

    private final FindRecipeIngredientVolumePort findRecipeIngredientVolumePort;

    @Override
    public List<InRecipeIngredientVolumeDTO> getRecipeIngredientVolumeList(Long recipeId) {
        return findRecipeIngredientVolumePort.getRecipeIngredientVolumes(recipeId);
    }
}
