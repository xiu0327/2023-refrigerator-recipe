package refrigerator.back.ingredient.application.port.in;

import java.time.LocalDateTime;

public interface ModifyIngredientUseCase {

    void modify(Long id, LocalDateTime expirationDate,
                String capacity, String storageMethod, String email);

}
