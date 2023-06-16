package refrigerator.back.ingredient.application.port.in;


import refrigerator.back.ingredient.adapter.in.dto.request.RecipeIngredientVolumeDTO;

import java.util.List;

public interface DeductionIngredientVolumeUseCase {
    void deduction(String memberId, List<RecipeIngredientVolumeDTO> ingredients);
}
