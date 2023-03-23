package refrigerator.back.ingredient.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.ingredient.application.port.in.AllLookUpIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.SearchIngredientByDeadlineUseCase;
import refrigerator.back.ingredient.application.port.in.SearchIngredientByFilterUseCase;
import refrigerator.back.ingredient.application.port.in.SingleLookUpIngredientUseCase;

@RestController
@RequiredArgsConstructor
public class LookUpIngredientController {

    private final AllLookUpIngredientUseCase allLookUpIngredientUseCase;
    private final SingleLookUpIngredientUseCase singleLookUpIngredientUseCase;
    private final SearchIngredientByFilterUseCase searchIngredientByFilterUseCase;
    private final SearchIngredientByDeadlineUseCase searchIngredientByDeadlineUseCase;

    //@GetMapping("api/ingredient/")
}
