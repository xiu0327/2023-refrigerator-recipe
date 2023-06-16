package refrigerator.back.recipe_recommend.application.port.out;

import java.util.Set;

public interface GetMyIngredientNameDataPort {
    Set<String> findMyIngredientNames(String memberId);
}
