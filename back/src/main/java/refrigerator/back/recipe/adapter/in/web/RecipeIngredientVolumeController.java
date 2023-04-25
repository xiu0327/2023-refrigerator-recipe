package refrigerator.back.recipe.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.common.BasicListResponseDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeIngredientVolumeDTO;
import refrigerator.back.recipe.application.port.in.FindRecipeIngredientVolumeListUseCase;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipeIngredientVolumeController {

    private final FindRecipeIngredientVolumeListUseCase findRecipeIngredientVolumeListUseCase;

    @GetMapping("/api/recipe/{recipeId}/ingredient/volume")
    public BasicListResponseDTO<InRecipeIngredientVolumeDTO> getRecipeIngredientVolumes(@PathVariable("recipeId") Long recipeId){
        return new BasicListResponseDTO<>(
                findRecipeIngredientVolumeListUseCase.getRecipeIngredientVolumeList(recipeId));
    }
}
