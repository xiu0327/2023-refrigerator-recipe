package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.ingredient.application.port.in.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.SaveIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.WriteIngredient;

@Service
@RequiredArgsConstructor

public class IngredientService implements SaveIngredientUseCase, ModifyIngredientUseCase, RemoveIngredientUseCase {

    private final WriteIngredient writeIngredient;

}
