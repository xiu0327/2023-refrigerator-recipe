package refrigerator.server.api.recipe_recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.recipe_recommend.application.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe_recommend.application.port.in.RecommendRecipeUseCase;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipeRecommendController {

    private final RecommendRecipeUseCase recommendRecipeUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @GetMapping("/api/recipe/recommend")
    public BasicListResponseDTO<InRecipeRecommendDTO> recommend(){
        List<InRecipeRecommendDTO> data = recommendRecipeUseCase.recommend(memberInformation.getMemberEmail());
        return new BasicListResponseDTO<>(data);
    }

}
