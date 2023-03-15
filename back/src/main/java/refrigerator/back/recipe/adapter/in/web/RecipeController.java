package refrigerator.back.recipe.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.recipe.adapter.dto.RecipeCourseDtoList;
import refrigerator.back.recipe.adapter.dto.RecipeDetailDTO;
import refrigerator.back.recipe.adapter.dto.RecipeListDTO;
import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;
import refrigerator.back.recipe.application.port.in.FindRecipeCourseUseCase;
import refrigerator.back.recipe.application.port.in.FindRecipeDetailUseCase;
import refrigerator.back.recipe.application.port.in.FindRecipeListUseCase;
import refrigerator.back.myscore.application.port.in.FindMyRecipeScoreUseCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeDtoMapper recipeDTOMapper;
    private final FindRecipeDetailUseCase findRecipeDetailUseCase;
    private final FindRecipeListUseCase findRecipeListUseCase;
    private final FindRecipeCourseUseCase findRecipeCourseUseCase;
    private final FindMyRecipeScoreUseCase findMyRecipeScoreUseCase;

    @GetMapping("/api/recipes/{recipeID}")
    public RecipeDetailDTO findRecipeByID(@PathVariable("recipeID") Long recipeID,
                                          HttpServletRequest request,
                                          HttpServletResponse response){
        /* 스프링 시큐리티에서 email 들고올 예정 */
        String email = "";
        RecipeViewsCookie cookie = new RecipeViewsCookie(recipeID);
        boolean isViewed = cookie.changeViewed(request);
        if (!isViewed){
            response.addCookie(cookie.createCookie());
        }
        RecipeDomain recipe = findRecipeDetailUseCase.getRecipe(recipeID, isViewed);
        double myScore = findMyRecipeScoreUseCase.getMyScore(email, recipeID);
        return recipeDTOMapper.recipeDetailMapper(recipe, myScore);
    }

    @GetMapping("/api/recipes/{recipeID}/course")
    public RecipeCourseDtoList findRecipeCourseByID(@PathVariable("recipeID") Long recipeID){
        List<RecipeCourseDomain> course = findRecipeCourseUseCase.getRecipeCourse(recipeID);
        return recipeDTOMapper.recipeCourseListMapper(course);
    }

    @GetMapping("/api/recipes")
    public RecipeListDTO findAllRecipe(@RequestParam("page") int page, @RequestParam(value = "size", defaultValue = "11") int size){
        List<RecipeDomain> recipeList = findRecipeListUseCase.getRecipeList(page, size);
        return recipeDTOMapper.recipeListMapper(recipeList);
    }
}
