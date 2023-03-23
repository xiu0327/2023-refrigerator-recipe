package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.ingredient.application.port.in.AllLookUpIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.SearchIngredientByDeadlineUseCase;
import refrigerator.back.ingredient.application.port.in.SearchIngredientByFilterUseCase;
import refrigerator.back.ingredient.application.port.in.SingleLookUpIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.ReadIngredient;

@Service
@RequiredArgsConstructor
public class LookUpIngredientService implements SearchIngredientByDeadlineUseCase, SearchIngredientByFilterUseCase,
                                                AllLookUpIngredientUseCase, SingleLookUpIngredientUseCase {

    private final ReadIngredient readIngredient;
}
