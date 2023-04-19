package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.ingredient.adapter.in.dto.*;
import refrigerator.back.ingredient.adapter.mapper.IngredientMapper;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.in.FindIngredientListUseCase;
import refrigerator.back.ingredient.application.port.in.FindIngredientDetailUseCase;

@RestController
@RequiredArgsConstructor
public class IngredientLookUpController {

    private final FindIngredientDetailUseCase findIngredientDetailUseCase;
    private final FindIngredientListUseCase findIngredientListUseCase;
    private final IngredientMapper mapper;

    @GetMapping("api/ingredients/")
    public IngredientListResponseDTO<IngredientResponseDTO> findIngredientList(@RequestBody IngredientLookUpRequestDTO requestDTO,
                                                                               @RequestParam("page") int page,
                                                                               @RequestParam(value = "size", defaultValue = "12") int size) {

        String email = "";

        return new IngredientListResponseDTO<>(
                findIngredientListUseCase.getIngredientList(
                        mapper.toIngredientSearchCondition(requestDTO, email), page, size));
    }

    @GetMapping("api/ingredient/search")
    public IngredientListResponseDTO<IngredientResponseDTO> searchIngredientList(@RequestParam("name") String name) {
        String email = "";
        return new IngredientListResponseDTO<>(findIngredientListUseCase.getIngredientListOfAll(email));
    }

    @GetMapping("api/ingredients/registered")
    public IngredientListResponseDTO<IngredientRegisteredResponseDTO> findIngredientListOfRegistered() {
        return new IngredientListResponseDTO<>(findIngredientListUseCase.getIngredientListOfRegistered());
    }

    @GetMapping("api/ingredients/{ingredientId}")
    public IngredientDetailResponseDTO findIngredient(@PathVariable("ingredientId") Long id) {
        return findIngredientDetailUseCase.getIngredient(id);
    }

    @GetMapping("api/ingredients/deadline/{days}")
    public IngredientListResponseDTO<IngredientResponseDTO> findIngredientListByDeadline(@PathVariable("days") Long days) {
        String email = "";
        return new IngredientListResponseDTO<>(findIngredientListUseCase.getIngredientListByDeadline(days, email));
    }
}
