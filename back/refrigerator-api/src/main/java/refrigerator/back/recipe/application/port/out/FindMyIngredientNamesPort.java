package refrigerator.back.recipe.application.port.out;

import java.util.Set;

public interface FindMyIngredientNamesPort {
    Set<String> findMyIngredientNames(String memberId);
}
