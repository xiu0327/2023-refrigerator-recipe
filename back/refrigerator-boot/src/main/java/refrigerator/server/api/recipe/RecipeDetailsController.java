package refrigerator.server.api.recipe;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.back.global.image.ImageGenerator;
import refrigerator.back.recipe.application.domain.dto.RecipeDto;
import refrigerator.back.recipe.application.port.in.FindRecipeDetailsUseCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class RecipeDetailsController {

    private final FindRecipeDetailsUseCase recipeUseCase;
    private final ImageGenerator recipeImageGenerator;
    private final GetMemberEmailUseCase memberInformation;

    @GetMapping("/api/recipe/{recipeId}/details")
    public RecipeDto findRecipeOne(@PathVariable("recipeId") Long recipeId,
                                   HttpServletRequest request,
                                   HttpServletResponse response){
        RecipeViewsCookie cookie = new RecipeViewsCookie(request.getCookies());
        boolean isViewed = cookie.isViewed(recipeId);
        if (!isViewed){
            cookie.addViewCookie(recipeId, response);
        }
        RecipeDto dto = recipeUseCase.findRecipeDetails(recipeId, memberInformation.getMemberEmail(), isViewed);
        dto.generateImageUrl(recipeImageGenerator);
        return dto;
    }
}
