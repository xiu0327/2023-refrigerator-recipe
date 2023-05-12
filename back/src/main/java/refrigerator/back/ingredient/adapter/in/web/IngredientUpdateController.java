package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.ingredient.adapter.in.dto.request.IngredientListRemoveRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.request.IngredientProposeRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.request.IngredientRegisterRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.request.IngredientUpdateRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientRegisterResponseDTO;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.in.FindRegisteredIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RegisterIngredientUseCase;

import javax.validation.Valid;

import static refrigerator.back.global.common.MemberInformation.*;
import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

@RestController
@RequiredArgsConstructor
public class IngredientUpdateController {

    final private RegisterIngredientUseCase registerIngredientUseCase;
    final private ModifyIngredientUseCase modifyIngredientUseCase;
    final private RemoveIngredientUseCase removeIngredientUseCase;
    final private FindRegisteredIngredientUseCase findRegisteredIngredientUseCase;

    @PostMapping("/api/ingredients")
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientRegisterResponseDTO registerIngredient(@RequestBody @Valid IngredientRegisterRequestDTO request, BindingResult bindingResult) {
        ingredientExceptionHandle(bindingResult);
        RegisteredIngredient ingredientUnit = findRegisteredIngredientUseCase.getIngredient(request.getName());

        Long id = registerIngredientUseCase.registerIngredient(request.getName(), request.getExpirationDate(), request.getVolume(),
                                                               ingredientUnit.getUnit(), request.getStorage(), ingredientUnit.getImage(), getMemberEmail());
        return new IngredientRegisterResponseDTO(id);
    }

    @PutMapping("/api/ingredients/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyIngredient(@PathVariable("ingredientId") Long id, @RequestBody @Valid IngredientUpdateRequestDTO request, BindingResult bindingResult) {
        ingredientExceptionHandle(bindingResult);

        modifyIngredientUseCase.modifyIngredient(id, request.getExpirationDate(), request.getVolume(), request.getStorage());
    }

    @DeleteMapping("/api/ingredients/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeIngredient(@PathVariable("ingredientId") Long id) {
        removeIngredientUseCase.removeIngredient(id);
    }

    @DeleteMapping("/api/ingredients")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllIngredient(@RequestBody @Valid IngredientListRemoveRequestDTO request, BindingResult bindingResult) {
        ingredientExceptionHandle(bindingResult);

        removeIngredientUseCase.removeAllIngredients(request.getRemoveIds());
    }

    @PostMapping("/api/ingredients/propose")
    @ResponseStatus(HttpStatus.CREATED)
    public void proposeIngredient(@RequestBody @Valid IngredientProposeRequestDTO request, BindingResult bindingResult) {
        ingredientExceptionHandle(bindingResult);

        registerIngredientUseCase.proposeIngredient(request.getName(), request.getUnit(), getMemberEmail());
    }


}
