package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.ingredient.adapter.dto.IngredientRegisterRequestDTO;
import refrigerator.back.ingredient.adapter.dto.IngredientRegisterResponseDTO;
import refrigerator.back.ingredient.adapter.dto.IngredientUpdateRequestDTO;
import refrigerator.back.ingredient.application.port.in.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RegisterIngredientUseCase;

@RestController
@RequiredArgsConstructor
public class IngredientController {

    final private RegisterIngredientUseCase registerIngredientUseCase;
    final private ModifyIngredientUseCase modifyIngredientUseCase;
    final private RemoveIngredientUseCase removeIngredientUseCase;

    @PostMapping("/api/ingredient")
    @ResponseStatus(HttpStatus.OK) // 향후 수정
    public IngredientRegisterResponseDTO register(@RequestBody IngredientRegisterRequestDTO request) {
        String email = "";
        Long ingredientId = registerIngredientUseCase.register(request.getName(), request.getExpirationDate(),
                request.getCapacity(), request.getCapacityUnit(), request.getStorageMethod(), email);

        return new IngredientRegisterResponseDTO(ingredientId);
    }

    @PutMapping("/api/ingredient/{ingredientId}")
    @ResponseStatus(HttpStatus.OK) // 향후 수정
    public void modify(@PathVariable("ingredientId") Long id, @RequestBody IngredientUpdateRequestDTO request) {
        String email = "";
        modifyIngredientUseCase.modify(id, request.getExpirationDate(), request.getCapacity(),
                                        request.getStorageMethod(), email);
    }

    @DeleteMapping("/api/ingredient/{ingredientId}")
    @ResponseStatus(HttpStatus.OK) // 향후 수정
    public void remove(@PathVariable("ingredientId") Long id) {
        String email = "";
        removeIngredientUseCase.remove(id, email);
    }
}
