package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.ingredient.adapter.in.dto.request.IngredientRegisterRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientRegisterResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.request.IngredientUpdateRequestDTO;
import refrigerator.back.ingredient.application.port.in.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RegisterIngredientUseCase;

@RestController
@RequiredArgsConstructor
public class IngredientController {

    final private RegisterIngredientUseCase registerIngredientUseCase;
    final private ModifyIngredientUseCase modifyIngredientUseCase;
    final private RemoveIngredientUseCase removeIngredientUseCase;

    @PostMapping("/api/ingredient") // 1:N 관계는 s를 붙여야함
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientRegisterResponseDTO register(@RequestBody IngredientRegisterRequestDTO request) {
        String email = "";
        Long id = registerIngredientUseCase.register(request.getName(), request.getExpirationDate(), request.getCapacity(),
                                                     request.getCapacityUnit(), request.getStorageMethod(), email);

        return new IngredientRegisterResponseDTO(id);
    }

    @PutMapping("/api/ingredient/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modify(@PathVariable("ingredientId") Long id, @RequestBody IngredientUpdateRequestDTO request) {
        String email = "";
        modifyIngredientUseCase.modify(id, request.getExpirationDate(), request.getCapacity(),
                                        request.getStorageMethod(), email);
    }

    @DeleteMapping("/api/ingredient/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("ingredientId") Long id) {
        String email = "";
        removeIngredientUseCase.remove(id, email);
    }
}
