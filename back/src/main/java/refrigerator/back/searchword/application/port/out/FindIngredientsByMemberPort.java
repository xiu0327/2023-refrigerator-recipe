package refrigerator.back.searchword.application.port.out;

import refrigerator.back.searchword.application.domain.Ingredient;

import java.util.List;

public interface FindIngredientsByMemberPort {
    List<String> getIngredients(String memberId);
}
