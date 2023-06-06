package refrigerator.back.recipe.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import refrigerator.back.recipe.adapter.in.dto.InRecipeBasicListDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeCourseDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDetailDTO;
import refrigerator.back.recipe.adapter.mapper.RecipeDtoMapper;
import refrigerator.back.recipe.application.domain.entity.Recipe;
import refrigerator.back.recipe.application.port.in.FindRecipeCourseUseCase;
import refrigerator.back.recipe.application.port.in.FindRecipeDetailUseCase;
import refrigerator.back.recipe.application.port.in.FindRecipeListUseCase;
import refrigerator.back.recipe.application.service.RecipeFormatService;
import refrigerator.back.recipe.infra.redis.config.RecipeCacheKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final FindRecipeDetailUseCase findRecipeDetailUseCase;
    private final FindRecipeListUseCase findRecipeListUseCase;
    private final FindRecipeCourseUseCase findRecipeCourseUseCase;
    private final RecipeDtoMapper mapper;
    private final RecipeFormatService recipeFormatService;
    private final MakeRecipeImageUrlAdapter makeRecipeImageUrl;

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
        InRecipeDetailDTO dto = transRecipeDto(recipe);
        makeRecipeImageUrl.toUrlByRecipeDetailDto(dto);
        return dto;
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
        List<InRecipeDTO> data = findRecipeListUseCase.getRecipeList(page, size);
        makeRecipeImageUrl.toUrlByRecipeDto(data);
        return new InRecipeBasicListDTO<>(data);
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
