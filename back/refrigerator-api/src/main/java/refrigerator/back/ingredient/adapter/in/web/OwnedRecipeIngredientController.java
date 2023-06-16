package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.common.api.BasicListResponseDTO;
import refrigerator.back.ingredient.application.port.in.MatchIngredientByRecipeUseCase;

import static refrigerator.back.global.common.api.MemberInformation.*;

@RestController
@RequiredArgsConstructor
public class OwnedRecipeIngredientController {

    private final MatchIngredientByRecipeUseCase matchIngredientByRecipeUseCase;

    @GetMapping("/api/ingredients/owned/{recipeId}")
    public BasicListResponseDTO<Long> getOwnedRecipeIngredients(@PathVariable("recipeId") Long recipeId){
        return new BasicListResponseDTO<>(matchIngredientByRecipeUseCase.getData(getMemberEmail(), recipeId));
    }
}
