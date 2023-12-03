package refrigerator.back.ingredient.outbound.adapater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.admin.FindUnitNamePort;
import refrigerator.back.ingredient.application.port.admin.SaveRegisteredIngredientPort;
import refrigerator.back.ingredient.exception.IngredientExceptionType;
import refrigerator.back.ingredient.outbound.repository.SubIngredientQueryRepository;

@Component
@RequiredArgsConstructor
public class IngredientAdminAdapter implements FindUnitNamePort, SaveRegisteredIngredientPort{

    private final SubIngredientQueryRepository subIngredientQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public String findUnitName(String name) {
        return subIngredientQueryRepository.findUnitName(name)
                .orElseThrow(() -> new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT));
    }

    @Override
    @Transactional
    public Long saveRegisteredIngredient(RegisteredIngredient registeredIngredient) {
        return subIngredientQueryRepository.saveRegisteredIngredient(registeredIngredient);
    }
}
