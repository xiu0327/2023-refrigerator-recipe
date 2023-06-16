package refrigerator.back.recipe_recommend.application.port.out;
import refrigerator.back.recipe_recommend.application.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe_recommend.application.domain.RecipeIngredientTuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GetRecipeRecommendInfoDataPort {
    List<InRecipeRecommendDTO> findInfoByIds(List<Long> ids);
    Map<Long, Set<RecipeIngredientTuple>> findRecipeIngredientNames();
}
