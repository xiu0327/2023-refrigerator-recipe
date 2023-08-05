package refrigerator.back.ingredient.application.port.out.ingredient.lookUp;

import refrigerator.back.ingredient.application.domain.MyIngredientCollection;

import java.time.LocalDate;

public interface FindMyIngredientMapPort {
    MyIngredientCollection getMap(String memberId, LocalDate now);
}
