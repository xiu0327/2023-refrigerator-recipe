package refrigerator.back.recipe_recommend.application.port.out;

import refrigerator.back.recipe_recommend.application.dto.MyIngredientDto;

import java.time.LocalDate;
import java.util.List;

public interface FindMyIngredientsPort {
    List<MyIngredientDto> findMyIngredients(LocalDate startDate, String memberId);
}
