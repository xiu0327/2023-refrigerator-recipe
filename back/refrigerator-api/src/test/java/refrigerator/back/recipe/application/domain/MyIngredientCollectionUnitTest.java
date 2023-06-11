package refrigerator.back.recipe.application.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MyIngredientCollectionUnitTest {

    @Test
    @DisplayName("식재료 소유 여부 확인")
    void checkOwnedIngredient() {
        Map<String, Double> myIngredients = new HashMap<>();
        myIngredients.put("사과", 75.0);
        myIngredients.put("계란", 8.0);
        MyIngredientCollection collection = new MyIngredientCollection(myIngredients);
        assertThat(collection.checkOwnedIngredient("사과", 90.0)).isFalse();
        assertThat(collection.checkOwnedIngredient("계란", 7.0)).isTrue();
    }
}