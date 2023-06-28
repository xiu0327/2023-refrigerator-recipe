package refrigerator.back.ingredient.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.repository.IngredientQuerySubRepository;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.out.registeredIngredient.FindRegisteredIngredientPort;
import refrigerator.back.ingredient.exception.IngredientExceptionType;


import java.util.List;

@Component
@RequiredArgsConstructor
public class RegisteredIngredientAdapter implements FindRegisteredIngredientPort {

    private final IngredientQuerySubRepository IngredientSubQueryRepository;

    @Override
    public List<RegisteredIngredient> findIngredientList() {
        return IngredientSubQueryRepository.getIngredientList();
    }

    @Override
    public RegisteredIngredient findIngredient(String name) {
        return IngredientSubQueryRepository.getIngredientList()
                .stream()
                .filter(ingredient -> ingredient.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new BusinessException(IngredientExceptionType.NOT_FOUND_REGISTERED_INGREDIENT));
    }

}
