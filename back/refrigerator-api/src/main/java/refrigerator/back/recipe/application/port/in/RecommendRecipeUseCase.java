package refrigerator.back.recipe.application.port.in;


import refrigerator.back.recipe.adapter.in.dto.InRecipeRecommendDTO;

import java.util.List;

public interface RecommendRecipeUseCase {
    List<InRecipeRecommendDTO> recommend(String memberId);
}
