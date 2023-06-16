package refrigerator.back.recipe.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.image.ImageGenerator;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDto;
import refrigerator.back.recipe.application.port.in.AddRecipeViewCountUseCase;
import refrigerator.back.recipe.application.port.in.FindRecipeDetailsUseCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static refrigerator.back.global.common.api.MemberInformation.*;

@RestController
@RequiredArgsConstructor
public class RecipeDetailsController {

    private final FindRecipeDetailsUseCase recipeUseCase;
    private final AddRecipeViewCountUseCase viewCountUseCase;
    private final ImageGenerator recipeImageGenerator;

    @GetMapping("/api/recipe/{recipeId}/details")
    public InRecipeDto findRecipeOne(@PathVariable("recipeId") Long recipeId,
                                      HttpServletRequest request,
                                      HttpServletResponse response){
        InRecipeDto dto = recipeUseCase.findRecipeDetails(recipeId, getMemberEmail());
        dto.generateImageUrl(recipeImageGenerator);
        RecipeViewsCookie cookie = new RecipeViewsCookie(request.getCookies());
        boolean isViewed = cookie.isViewed(recipeId);
        if (!isViewed){
            cookie.addViewCookie(recipeId, response);
        }
        viewCountUseCase.add(isViewed, recipeId);
        return dto;
    }
}
