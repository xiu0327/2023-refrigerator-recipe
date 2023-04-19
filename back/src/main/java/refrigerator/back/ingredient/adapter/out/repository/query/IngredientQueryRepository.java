package refrigerator.back.ingredient.adapter.out.repository.query;

import com.querydsl.core.Fetchable;
import org.springframework.data.domain.Pageable;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;

import java.util.List;

public interface IngredientQueryRepository {
    List<RegisteredIngredient> findRegisteredIngredient();

    List<Ingredient> findIngredientList(IngredientSearchCondition condition, Pageable pageable);

    void saveSuggestIngredient(SuggestedIngredient ingredient);
}
