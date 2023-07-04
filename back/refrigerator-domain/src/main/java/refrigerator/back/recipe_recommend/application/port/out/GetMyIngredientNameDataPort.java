package refrigerator.back.recipe_recommend.application.port.out;

import java.time.LocalDate;
import java.util.Set;

public interface GetMyIngredientNameDataPort {
    Set<String> findMyIngredientNames(LocalDate now, String memberId);
}
