package refrigerator.back.recipe.application.port.out;

import java.util.Set;

public interface FindIngredientNamesPort {
    Set<String> findIngredientNames(String memberId);
}
