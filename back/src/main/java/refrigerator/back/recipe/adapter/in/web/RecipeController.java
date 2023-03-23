package refrigerator.back.recipe.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.recipe.adapter.in.cache.config.RecipeCacheKey;
import refrigerator.back.recipe.adapter.in.dto.*;
import refrigerator.back.recipe.application.port.in.FindRecipeCourseUseCase;
import refrigerator.back.recipe.application.port.in.FindRecipeDetailUseCase;
import refrigerator.back.recipe.application.port.in.FindRecipeListUseCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final FindRecipeDetailUseCase findRecipeDetailUseCase;
    private final FindRecipeListUseCase findRecipeListUseCase;
    private final FindRecipeCourseUseCase findRecipeCourseUseCase;

    @GetMapping("/api/recipes/{recipeID}")
    public InRecipeDetailDTO findRecipeByID(@PathVariable("recipeID") Long recipeID,
                                            HttpServletRequest request,
                                            HttpServletResponse response){
        RecipeViewsCookie cookie = new RecipeViewsCookie(recipeID);
        boolean isViewed = cookie.changeViewed(request);
        if (!isViewed){
            response.addCookie(cookie.createCookie());
        }
        return findRecipeDetailUseCase.getRecipe(recipeID, isViewed);
    }

    @GetMapping("/api/recipes/{recipeID}/course")
    public InRecipeBasicListDTO<InRecipeCourseDTO> findRecipeCourseByID(@PathVariable("recipeID") Long recipeID){
        return findRecipeCourseUseCase.getRecipeCourse(recipeID);
    }

    @GetMapping("/api/recipes")
    @Cacheable(value = RecipeCacheKey.RECIPE, key = "'recipes_' + #page", cacheManager = "recipeCacheManager")
    public InRecipeBasicListDTO<InRecipeDTO> findAllRecipe(
            @RequestParam("page") int page,
            @RequestParam(value = "size", defaultValue = "11") int size){
        return findRecipeListUseCase.getRecipeList(page, size);
    }
}
