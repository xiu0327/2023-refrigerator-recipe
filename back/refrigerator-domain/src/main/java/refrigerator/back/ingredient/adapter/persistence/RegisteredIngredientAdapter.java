package refrigerator.back.ingredient.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.repository.SubIngredientQueryRepository;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.out.registeredIngredient.FindRegisteredIngredientPort;
import refrigerator.back.ingredient.exception.IngredientExceptionType;


import java.util.List;

@Component
@RequiredArgsConstructor
public class RegisteredIngredientAdapter implements FindRegisteredIngredientPort {

    private final SubIngredientQueryRepository subIngredientQueryRepository;

    @Override
    public List<RegisteredIngredient> findIngredientList() {
        return subIngredientQueryRepository.getRegisteredIngredientList();
    }

    @Override
    public RegisteredIngredient findIngredient(String name) {
        return subIngredientQueryRepository.getRegisteredIngredientList()
                .stream()
                .filter(ingredient -> ingredient.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new BusinessException(IngredientExceptionType.NOT_FOUND_REGISTERED_INGREDIENT));
    }

}
