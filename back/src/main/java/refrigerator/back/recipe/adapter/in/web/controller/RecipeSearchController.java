package refrigerator.back.recipe.adapter.in.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.recipe.adapter.in.cache.config.RecipeCacheKey;
import refrigerator.back.recipe.adapter.in.dto.RecipeConditionListDTO;
import refrigerator.back.recipe.adapter.in.dto.RecipeListDTO;
import refrigerator.back.recipe.adapter.in.web.mapper.RecipeInboundDtoMapper;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;
import refrigerator.back.recipe.application.port.in.FindSearchConditionUseCase;
import refrigerator.back.recipe.application.port.in.SearchRecipeUseCase;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipeSearchController {

    private final SearchRecipeUseCase searchRecipeUseCase;
    private final FindSearchConditionUseCase findSearchConditionUseCase;
    private final RecipeInboundDtoMapper mapper;

    @GetMapping("/api/recipe/search")
    public RecipeListDTO search(@RequestParam("recipeType") String recipeType,
                                      @RequestParam("foodType") String foodType,
                                      @RequestParam("level") String difficulty,
                                      @RequestParam("score") Double score,
                                      @RequestParam("page") int page,
                                      @RequestParam(value = "size", defaultValue = "11") int size){
        List<RecipeDomain> result = searchRecipeUseCase.search(recipeType, foodType, difficulty, score, page, size);
        return mapper.recipeListMapper(result);
    }

    @GetMapping("/api/recipe/search/condition/food-type")
    @Cacheable(value = RecipeCacheKey.FOOD_TYPE,
            key="'condition_food_type'",
            cacheManager = "recipeFoodTypeCacheManager")
    public RecipeConditionListDTO getConditionByFoodType(){
        return new RecipeConditionListDTO(findSearchConditionUseCase.findRecipeFoodTypeCond());
    }

    @GetMapping("/api/recipe/search/condition/category")
    @Cacheable(value = RecipeCacheKey.CATEGORY,
            key="'condition_category'",
            cacheManager = "recipeCategoryCacheManager")
    public RecipeConditionListDTO getConditionByCategory(){
        return new RecipeConditionListDTO(findSearchConditionUseCase.findRecipeCategoryCond());
    }

}
