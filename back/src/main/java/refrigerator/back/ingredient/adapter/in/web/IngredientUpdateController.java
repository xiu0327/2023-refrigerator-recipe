package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.ingredient.adapter.in.dto.*;
import refrigerator.back.ingredient.application.port.in.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RegisterIngredientUseCase;

import static refrigerator.back.global.common.MemberInformation.*;

@RestController
@RequiredArgsConstructor
public class IngredientUpdateController {

    final private RegisterIngredientUseCase registerIngredientUseCase;
    final private ModifyIngredientUseCase modifyIngredientUseCase;
    final private RemoveIngredientUseCase removeIngredientUseCase;

    @PostMapping("/api/ingredients")
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientRegisterResponseDTO registerIngredient(@RequestBody IngredientRegisterRequestDTO request) {
        Long id = registerIngredientUseCase.registerIngredient(request.getName(), request.getExpirationDate(), request.getCapacity(),
                                                               request.getCapacityUnit(), request.getStorageMethod(), request.getImageId(), getMemberEmail());

        return new IngredientRegisterResponseDTO(id);
    }

    @PutMapping("/api/ingredients/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyIngredient(@PathVariable("ingredientId") Long id, @RequestBody IngredientUpdateRequestDTO request) {
        modifyIngredientUseCase.modifyIngredient(id, request.getExpirationDate(), request.getCapacity(), request.getStorageMethod());
    }

    @DeleteMapping("/api/ingredients/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeIngredient(@PathVariable("ingredientId") Long id) {
        removeIngredientUseCase.removeIngredient(id);
    }

    @DeleteMapping("/api/ingredients/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllIngredient(@RequestBody IngredientListRemoveRequestDTO request) {
        removeIngredientUseCase.removeAllIngredients(request.getRemoveIds());
    }

    @PostMapping("/api/ingredients/propose")
    @ResponseStatus(HttpStatus.CREATED)
    public void proposeIngredient(@RequestBody IngredientProposeRequestDTO request) {
        registerIngredientUseCase.proposeIngredient(request.getName(), request.getCapacityUnit(), getMemberEmail());
    }
}
