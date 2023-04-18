package refrigerator.back.recipe.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.recipe.adapter.in.cache.config.RecipeCacheKey;
import refrigerator.back.recipe.adapter.in.dto.InRecipeBasicListDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeSearchRequestDTO;
import refrigerator.back.recipe.adapter.mapper.RecipeDtoMapper;
import refrigerator.back.recipe.application.port.in.FindSearchConditionUseCase;
import refrigerator.back.recipe.application.port.in.SearchRecipeUseCase;


@RestController
@RequiredArgsConstructor
public class RecipeSearchController {

    private final SearchRecipeUseCase searchRecipeUseCase;
    private final FindSearchConditionUseCase findSearchConditionUseCase;
    private final RecipeDtoMapper mapper;

    @GetMapping("/api/recipe/search")
    public InRecipeBasicListDTO<InRecipeDTO> search(@RequestBody InRecipeSearchRequestDTO condition,
                                @RequestParam("page") int page,
                                @RequestParam(value = "size", defaultValue = "11") int size){
        return searchRecipeUseCase.search(
                mapper.toRecipeSearchCondition(condition),
                page, size);
    }

    @GetMapping("/api/recipe/search/condition/food-type")
    @Cacheable(value = RecipeCacheKey.FOOD_TYPE,
            key="'condition_food_type'",
            cacheManager = "recipeFoodTypeCacheManager")
    public InRecipeBasicListDTO<String> getConditionByFoodType(){
        return new InRecipeBasicListDTO<>(findSearchConditionUseCase.findRecipeFoodTypeCond());
    }

    @GetMapping("/api/recipe/search/condition/category")
    @Cacheable(value = RecipeCacheKey.CATEGORY,
            key="'condition_category'",
            cacheManager = "recipeCategoryCacheManager")
    public InRecipeBasicListDTO<String> getConditionByCategory(){
        return new InRecipeBasicListDTO<>(findSearchConditionUseCase.findRecipeCategoryCond());
    }

    @GetMapping("/api/recipe/search/condition/recipe-type")
    @Cacheable(value = RecipeCacheKey.RECIPE_TYPE,
            key="'condition_recipe_type'",
            cacheManager = "recipeTypeCacheManager")
    public InRecipeBasicListDTO<String> getConditionByRecipeType(){
        return new InRecipeBasicListDTO<>(findSearchConditionUseCase.findRecipeTypeCond());
    }

    @GetMapping("/api/recipe/search/condition/difficulty")
    @Cacheable(value = RecipeCacheKey.RECIPE_TYPE,
            key="'condition_difficulty'",
            cacheManager = "recipeDifficultyCacheManager")
    public InRecipeBasicListDTO<String> getConditionByRecipeDifficulty(){
        return new InRecipeBasicListDTO<>(findSearchConditionUseCase.findRecipeDifficultyCond());
    }

}
