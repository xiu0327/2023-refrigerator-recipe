package refrigerator.back.recipe_recommend.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.common.api.BasicListResponseDTO;
import refrigerator.back.global.image.ImageGenerator;
import refrigerator.back.recipe_recommend.adapter.in.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe_recommend.application.port.in.RecommendRecipeUseCase;


import java.util.List;

import static refrigerator.back.global.common.api.MemberInformation.*;

@RestController
@RequiredArgsConstructor
public class RecipeRecommendController {

    private final RecommendRecipeUseCase recommendRecipeUseCase;
    private final ImageGenerator recipeImageGenerator;

    @GetMapping("/api/recipe/recommend")
    public BasicListResponseDTO<InRecipeRecommendDTO> recommend(){
        List<InRecipeRecommendDTO> data = recommendRecipeUseCase.recommend(getMemberEmail());
        data.forEach(dto -> dto.generateImageUrl(recipeImageGenerator));
        return new BasicListResponseDTO<>(data);
    }

}
