package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientListResponseDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.port.in.LookUpIngredientListUseCase;
import refrigerator.back.ingredient.application.port.in.LookUpIngredientDetailUseCase;
import refrigerator.back.ingredient.application.port.in.SearchIngredientListUseCase;

@RestController
@RequiredArgsConstructor
public class LookUpIngredientController {

    private final LookUpIngredientDetailUseCase lookUpIngredientDetailUseCase;
    private final LookUpIngredientListUseCase lookUpIngredientListUseCase;
    private final SearchIngredientListUseCase searchIngredientListUseCase;

    @GetMapping("api/ingredients/")
    public IngredientListResponseDTO LookUpList(@RequestParam("storage") String storage,
                                                @RequestParam("deadline") boolean deadline) {

        String email = "";
        return new IngredientListResponseDTO(lookUpIngredientListUseCase.lookUpList(storage, deadline, email));
    }

    @GetMapping("api/ingredient/search")
    public IngredientListResponseDTO searchList(@RequestParam("name") String name) {
        String email = "";
        return new IngredientListResponseDTO(searchIngredientListUseCase.searchList(name, email));
    }

    @GetMapping("api/ingredient/{ingredientId}")
    public IngredientDetailResponseDTO LookUpDetail(@PathVariable("ingredientId") Long id) {
        String email = "";
        return lookUpIngredientDetailUseCase.lookUpDetail(id, email);

    }
}
