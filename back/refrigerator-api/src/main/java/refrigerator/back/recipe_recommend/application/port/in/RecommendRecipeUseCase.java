package refrigerator.back.recipe_recommend.application.port.in;


import refrigerator.back.recipe_recommend.adapter.in.dto.InRecipeRecommendDTO;

import java.util.List;

public interface RecommendRecipeUseCase {
    List<InRecipeRecommendDTO> recommend(String memberId);
}
