package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.application.port.in.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RegisterIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.ReadIngredient;
import refrigerator.back.ingredient.application.port.out.WriteIngredient;
import refrigerator.back.ingredient.exception.IngredientExceptionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientUpdateService implements RegisterIngredientUseCase, ModifyIngredientUseCase, RemoveIngredientUseCase {

    private final WriteIngredient writeIngredient;
    private final ReadIngredient readIngredient;

    @Override
    public Long registerIngredient(String name, LocalDate expirationDate, Integer capacity,
                                   String capacityUnit, String storageMethod, String image, String email) {

        return writeIngredient.saveIngredient(Ingredient.create(name, expirationDate, capacity,
                                    capacityUnit, storageMethod, image, email));
    }

    @Override
    public void proposeIngredient(String name, String capacityUnit) {
        writeIngredient.proposeIngredient(
                SuggestedIngredient.builder()
                .name(name)
                .unit(capacityUnit)
                .build()
        );
    }

    @Override
    public void modifyIngredient(Long id, LocalDate expirationDate, Integer capacity, String storageMethod) {
        Ingredient ingredient = readIngredient.getIngredientById(id);
        ingredient.modify(expirationDate, capacity, storageMethod);
        writeIngredient.saveIngredient(ingredient);
    }

    @Override
    public void removeIngredient(Long id) {
        Ingredient ingredient = readIngredient.getIngredientById(id);
        ingredient.delete();
        writeIngredient.saveIngredient(ingredient);
    }

    @Override
    public void removeAllIngredients(List<Long> ids) {
        for (Long id : ids) {
            Ingredient ingredient = readIngredient.getIngredientById(id);
            ingredient.delete();
            writeIngredient.saveIngredient(ingredient);
        }
    }
}
