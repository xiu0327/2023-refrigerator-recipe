package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.port.in.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RegisterIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.ReadIngredient;
import refrigerator.back.ingredient.application.port.out.WriteIngredient;
import refrigerator.back.ingredient.exception.IngredientExceptionType;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService implements RegisterIngredientUseCase, ModifyIngredientUseCase, RemoveIngredientUseCase {

    private final WriteIngredient writeIngredient;
    private final ReadIngredient readIngredient;

    @Override
    public Long register(String name, LocalDateTime expirationDate, String capacity,
                         String capacityUnit, String storageMethod, String email) {

        return writeIngredient.save(Ingredient.create(name, expirationDate, capacity,
                                    capacityUnit, storageMethod, email));
    }

    @Override
    public void modify(Long id, LocalDateTime expirationDate, String capacity, String storageMethod, String email) {
        Ingredient ingredient = readIngredient.findOne(id);
        if(ingredient.getEmail().equals(email)) {
            ingredient.modify(expirationDate, capacity, storageMethod);
            writeIngredient.update(ingredient);
        }
        else {
            throw new BusinessException(IngredientExceptionType.TEST_ERROR);
        }
    }

    @Override
    public void remove(Long id, String email) {
        Ingredient ingredient = readIngredient.findOne(id);
        if(ingredient.getEmail().equals(email)) {
            writeIngredient.delete(ingredient);
        }
        else {
            throw new BusinessException(IngredientExceptionType.TEST_ERROR);
        }
    }
}
