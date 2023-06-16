package refrigerator.back.ingredient.application.port.in;


import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientRegisterDTO;

import java.time.LocalDate;

public interface RegisterIngredientUseCase {

    IngredientRegisterDTO registerIngredient(String name, LocalDate expirationDate, Double capacity,
                                             String capacityUnit, IngredientStorageType storageMethod, Integer imageId, String email);

    void proposeIngredient(String name, String capacityUnit, String email);
}
