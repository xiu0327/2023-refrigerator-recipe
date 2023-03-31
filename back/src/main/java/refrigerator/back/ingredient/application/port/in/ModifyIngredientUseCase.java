package refrigerator.back.ingredient.application.port.in;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ModifyIngredientUseCase {

    void modifyIngredient(Long id, LocalDate expirationDate,
                          Integer capacity, String storageMethod);

}
