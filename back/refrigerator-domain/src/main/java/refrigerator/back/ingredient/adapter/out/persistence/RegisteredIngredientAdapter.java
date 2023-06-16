package refrigerator.back.ingredient.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.out.repository.module.RegisteredIngredientInit;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.out.FindRegisteredIngredientPort;
import refrigerator.back.ingredient.exception.IngredientExceptionType;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class RegisteredIngredientAdapter implements FindRegisteredIngredientPort {

    private final RegisteredIngredientInit registeredIngredientInit;

    @Override
    public List<RegisteredIngredient> findIngredientList() {
        return registeredIngredientInit.getIngredientList();
    }

    @Override
    public RegisteredIngredient findIngredient(String name) {
        return registeredIngredientInit.getIngredientList()
                .stream()
                .filter(ingredient -> ingredient.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new BusinessException(IngredientExceptionType.NOT_FOUND_REGISTERED_INGREDIENT));
    }

}
