package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.common.BasicListResponseDTO;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.back.ingredient.application.port.in.MatchIngredientByRecipeUseCase;


@RestController
@RequiredArgsConstructor
public class OwnedRecipeIngredientController {

    private final MatchIngredientByRecipeUseCase matchIngredientByRecipeUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @GetMapping("/api/ingredients/owned/{recipeId}")
    public BasicListResponseDTO<Long> getOwnedRecipeIngredients(@PathVariable("recipeId") Long recipeId){
        return new BasicListResponseDTO<>(matchIngredientByRecipeUseCase.getData(memberInformation.getMemberEmail(), recipeId));
    }
}
