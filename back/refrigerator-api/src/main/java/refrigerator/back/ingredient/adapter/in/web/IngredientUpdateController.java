package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.global.common.api.BasicListRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientProposeRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientRegisterRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientUpdateRequestDTO;
import refrigerator.back.ingredient.application.dto.IngredientRegisterDTO;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.in.FindRegisteredIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RegisterIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RemoveIngredientUseCase;


import javax.validation.Valid;

import static refrigerator.back.global.common.api.MemberInformation.*;
import static refrigerator.back.global.exception.api.ValidationExceptionHandler.*;
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
    public IngredientRegisterDTO registerIngredient(@RequestBody @Valid IngredientRegisterRequestDTO request, BindingResult bindingResult) {
        check(bindingResult, NOT_VALID_REQUEST_BODY);

        RegisteredIngredient ingredientUnit = findRegisteredIngredientUseCase.getIngredient(request.getName());

        Long id = registerIngredientUseCase.registerIngredient(request.getName(), request.getExpirationDate(), request.getVolume(),
                                                               ingredientUnit.getUnit(), request.getStorage(), ingredientUnit.getImage(), getMemberEmail());
        return new IngredientRegisterDTO(id);
    }

    @PutMapping("/api/ingredients/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyIngredient(@PathVariable("ingredientId") Long id, @RequestBody @Valid IngredientUpdateRequestDTO request, BindingResult bindingResult) {
        check(bindingResult, NOT_VALID_REQUEST_BODY);

        modifyIngredientUseCase.modifyIngredient(id, request.getExpirationDate(), request.getVolume(), request.getStorage());
    }

    @DeleteMapping("/api/ingredients/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeIngredient(@PathVariable("ingredientId") Long id) {
        removeIngredientUseCase.removeIngredient(id);
    }

    @DeleteMapping("/api/ingredients")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllIngredient(@RequestBody @Valid BasicListRequestDTO<Long> request, BindingResult bindingResult) {
        check(bindingResult, NOT_VALID_REQUEST_BODY);

        removeIngredientUseCase.removeAllIngredients(request.getData());
    }

    @PostMapping("/api/ingredients/propose")
    @ResponseStatus(HttpStatus.CREATED)
    public void proposeIngredient(@RequestBody @Valid IngredientProposeRequestDTO request, BindingResult bindingResult) {
        check(bindingResult, NOT_VALID_REQUEST_BODY);

        registerIngredientUseCase.proposeIngredient(request.getName(), request.getUnit(), getMemberEmail());
    }


}
