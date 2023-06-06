package refrigerator.back.recipe.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.common.BasicListResponseDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe.application.port.in.RecommendRecipeUseCase;


import java.util.List;

import static refrigerator.back.global.common.MemberInformation.*;

@RestController
@RequiredArgsConstructor
public class RecipeRecommendController {

    private final RecommendRecipeUseCase recommendRecipeUseCase;
    private final MakeRecipeImageUrlAdapter makeRecipeImageUrl;

    @GetMapping("/api/recipe/recommend")
    public BasicListResponseDTO<InRecipeRecommendDTO> recommend(){
        List<InRecipeRecommendDTO> data = recommendRecipeUseCase.recommend(getMemberEmail());
        makeRecipeImageUrl.toUrlByRecipeRecommendDto(data);
        return new BasicListResponseDTO<>(data);
    }

}
