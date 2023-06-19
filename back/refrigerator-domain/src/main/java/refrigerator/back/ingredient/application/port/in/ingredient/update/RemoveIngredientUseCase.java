package refrigerator.back.ingredient.application.port.in.ingredient.update;

import java.util.List;

public interface RemoveIngredientUseCase {
    void removeIngredient(Long id);

    void removeAllIngredients(List<Long> ids);
}
