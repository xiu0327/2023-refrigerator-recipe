package refrigerator.back.ingredient.application.port.in;

import java.time.LocalDateTime;

public interface RegisterIngredientUseCase {

    Long register(String name, LocalDateTime expirationDate, String capacity,
                String capacityUnit, String storageMethod, String email);
}
