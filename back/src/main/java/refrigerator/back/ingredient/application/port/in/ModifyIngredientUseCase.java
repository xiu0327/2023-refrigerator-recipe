package refrigerator.back.ingredient.application.port.in;

import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ModifyIngredientUseCase {

    void modifyIngredient(Long id, LocalDate expirationDate,
                          Double capacity, IngredientStorageType storageMethod);

}
