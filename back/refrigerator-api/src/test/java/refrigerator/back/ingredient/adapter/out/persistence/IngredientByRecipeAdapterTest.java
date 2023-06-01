package refrigerator.back.ingredient.adapter.out.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.adapter.in.dto.InRecipeIngredientVolumeDTO;
import refrigerator.back.recipe.application.port.out.FindRecipeIngredientVolumePort;

import java.util.List;

@SpringBootTest
@Transactional
class IngredientByRecipeAdapterTest {

    @Autowired FindRecipeIngredientVolumePort findRecipeIngredientVolumePort;

    @Test
    @DisplayName("레시피 용량 조회 -> 도메인 치환")
    void getRecipeIngredientVolumes() {
        List<InRecipeIngredientVolumeDTO> result = findRecipeIngredientVolumePort.getRecipeIngredientVolumes(1L);
        for (InRecipeIngredientVolumeDTO ingredientVolumeByRecipe : result) {
            Assertions.assertNotNull(ingredientVolumeByRecipe.getVolume());
            Assertions.assertNotNull(ingredientVolumeByRecipe.getName());
            Assertions.assertNotNull(ingredientVolumeByRecipe.getName());
        }
    }
}