package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.common.MemberInformation;
import refrigerator.back.ingredient.adapter.in.dto.RecipeIngredientVolumeRequestDTO;
import refrigerator.back.ingredient.application.port.in.DeductionIngredientVolumeUseCase;

@RestController
@RequiredArgsConstructor
public class IngredientDeductionController {

    private final DeductionIngredientVolumeUseCase deductionIngredientVolumeUseCase;

    @PutMapping("/api/ingredients/deduction")
    public void deductionIngredientVolume(@RequestBody RecipeIngredientVolumeRequestDTO request){
        deductionIngredientVolumeUseCase.deduction(
                MemberInformation.getMemberEmail(),
                request.getIngredients());
    }
}
