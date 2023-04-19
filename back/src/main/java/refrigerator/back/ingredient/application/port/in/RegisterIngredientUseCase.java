package refrigerator.back.ingredient.application.port.in;

import java.time.LocalDate;

public interface RegisterIngredientUseCase {

    Long registerIngredient(String name, LocalDate expirationDate, Integer capacity,
                            String capacityUnit, String storageMethod, Integer imageId, String email);

    void proposeIngredient(String name, String capacityUnit, String email);
}
