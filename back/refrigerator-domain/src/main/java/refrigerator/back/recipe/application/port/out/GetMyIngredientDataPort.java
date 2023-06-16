package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.application.domain.MyIngredientCollection;

public interface GetMyIngredientDataPort {
    MyIngredientCollection getMyIngredients(String memberId);
}
