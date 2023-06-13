package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.in.dto.request.RecipeIngredientVolumeDTO;
import refrigerator.back.ingredient.adapter.in.dto.request.RecipeIngredientVolumeRequestDTO;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.in.DeductionIngredientVolumeUseCase;
import refrigerator.back.ingredient.application.port.in.FindRegisteredIngredientUseCase;


import javax.validation.Valid;
import java.util.List;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

@RestController
@RequiredArgsConstructor
public class IngredientDeductionController {

    private final DeductionIngredientVolumeUseCase deductionIngredientVolumeUseCase;
    private final FindRegisteredIngredientUseCase findRegisteredIngredientUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @PutMapping("/api/ingredients/deduction")
    public void deductionIngredientVolume(@RequestBody @Valid RecipeIngredientVolumeRequestDTO request, BindingResult bindingResult){
        ingredientExceptionHandle(bindingResult);

        List<RegisteredIngredient> ingredientList = findRegisteredIngredientUseCase.getIngredientList();

        for (RecipeIngredientVolumeDTO dto : request.getIngredients()) {
             ingredientList.stream()
                    .filter(i -> i.getName().equals(dto.getName()) && i.getUnit().equals(dto.getUnit()))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(NOT_VALID_REQUEST_BODY));
        }

        deductionIngredientVolumeUseCase.deduction(
                memberInformation.getMemberEmail(),
                request.getIngredients());
    }
}
