package refrigerator.back.recipe.application.port.out;
import refrigerator.back.recipe.adapter.in.dto.InRecipeRecommendDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FindRecipeRecommendInfoPort {
    List<InRecipeRecommendDTO> findInfoByIds(List<Long> ids);
    Map<Long, Set<String>> findRecipeIngredientNames();
}
