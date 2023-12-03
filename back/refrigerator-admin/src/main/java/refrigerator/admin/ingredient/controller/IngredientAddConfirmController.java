package refrigerator.admin.ingredient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.admin.ingredient.dto.IngredientDto;
import refrigerator.back.ingredient.application.port.admin.AddConfirmIngredientUseCase;

@RestController
@RequiredArgsConstructor
public class IngredientAddConfirmController {

    private final AddConfirmIngredientUseCase addConfirmIngredientUseCase;

    @PostMapping("admin/ingredients/add/confirm")
    public void addConfirmIngredient(@RequestBody IngredientDto dto) {
        addConfirmIngredientUseCase.addConfirmIngredient(dto.getName(), dto.getImage());
    }

}
