package refrigerator.back.recipe.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.recipe.adapter.in.cache.config.RecipeCacheKey;
import refrigerator.back.recipe.adapter.in.dto.*;
import refrigerator.back.recipe.adapter.mapper.RecipeDtoMapper;
import refrigerator.back.recipe.application.domain.entity.Recipe;
import refrigerator.back.recipe.application.port.in.FindRecipeCourseUseCase;
import refrigerator.back.recipe.application.port.in.FindRecipeDetailUseCase;
import refrigerator.back.recipe.application.port.in.FindRecipeListUseCase;
import refrigerator.back.recipe.application.service.RecipeFormatService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final FindRecipeDetailUseCase findRecipeDetailUseCase;
    private final FindRecipeListUseCase findRecipeListUseCase;
    private final FindRecipeCourseUseCase findRecipeCourseUseCase;
    private final RecipeDtoMapper mapper;
    private final RecipeFormatService recipeFormatService;

    @GetMapping("/api/recipe/{recipeID}")
    public InRecipeDetailDTO findRecipeByID(@PathVariable("recipeID") Long recipeID,
                                            HttpServletRequest request,
                                            HttpServletResponse response){
        RecipeViewsCookie cookie = new RecipeViewsCookie(recipeID);
        boolean isViewed = cookie.changeViewed(request);
        if (!isViewed){
            response.addCookie(cookie.createCookie());
        }
        Recipe recipe = findRecipeDetailUseCase.getRecipe(recipeID, isViewed);
        return transRecipeDto(recipe);
    }

    @GetMapping("/api/recipe/{recipeID}/course")
    public InRecipeBasicListDTO<InRecipeCourseDTO> findRecipeCourseByID(@PathVariable("recipeID") Long recipeID){
        return findRecipeCourseUseCase.getRecipeCourse(recipeID);
    }

    @GetMapping("/api/recipe")
    @Cacheable(value = RecipeCacheKey.RECIPE, key = "'recipes_' + #page", cacheManager = "recipeCacheManager")
    public InRecipeBasicListDTO<InRecipeDTO> findAllRecipe(
            @RequestParam("page") int page,
            @RequestParam(value = "size", defaultValue = "11") int size){
        return findRecipeListUseCase.getRecipeList(page, size);
    }

    private InRecipeDetailDTO transRecipeDto(Recipe recipe) {
        InRecipeDetailDTO dto = mapper.toInRecipeDetailsDto(
                recipe,
                recipe.getDetails(),
                recipe.getDetails().getScore().calculateScore());
        dto.setIngredients(recipe.getIngredients()
                .stream().map(mapper::toInRecipeIngredientDto)
                .collect(Collectors.toSet()));
        dto.settingFormat(
                recipeFormatService.changeServingsFormat(recipe.getServings()),
                recipeFormatService.changeKcalFormat(recipe.getKcal()),
                recipeFormatService.changeCookingTimeFormat(recipe.getCookingTime())
        );
        return dto;
    }
}
