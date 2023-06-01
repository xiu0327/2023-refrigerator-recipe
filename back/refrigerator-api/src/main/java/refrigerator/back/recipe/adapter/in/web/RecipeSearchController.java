package refrigerator.back.recipe.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.common.MemberInformation;
import refrigerator.back.recipe.adapter.in.dto.InRecipeBasicListDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeSearchRequestDTO;
import refrigerator.back.recipe.adapter.mapper.RecipeDtoMapper;
import refrigerator.back.recipe.application.port.in.FindSearchConditionUseCase;
import refrigerator.back.recipe.application.port.in.SearchRecipeUseCase;
import refrigerator.back.recipe.infra.redis.config.RecipeCacheKey;
import refrigerator.back.searchword.application.port.in.AddSearchWordUseCase;


@RestController
@RequiredArgsConstructor
public class RecipeSearchController {

    private final SearchRecipeUseCase searchRecipeUseCase;
    private final FindSearchConditionUseCase findSearchConditionUseCase;
    private final AddSearchWordUseCase addSearchWordUseCase;
    private final RecipeDtoMapper mapper;

    @GetMapping("/api/recipe/search")
    public InRecipeBasicListDTO<InRecipeDTO> search(
                    @RequestParam(value = "searchWord", defaultValue = "") String searchWord,
                    @RequestParam(value = "recipeType", defaultValue = "") String recipeType,
                    @RequestParam(value = "recipeFoodType", defaultValue = "") String recipeFoodType,
                    @RequestParam(value = "category", defaultValue = "") String category,
                    @RequestParam(value = "difficulty", defaultValue = "") String difficulty,
                    @RequestParam(value = "score", defaultValue = "0.0") Double score,
                    @RequestParam(value = "page", defaultValue = "0") int page,
                    @RequestParam(value = "size", defaultValue = "11") int size){
        if (StringUtils.hasText(searchWord)){
            addSearchWordUseCase.addSearchWord(MemberInformation.getMemberEmail(), searchWord);
        }
        return searchRecipeUseCase.search(
                mapper.toRecipeSearchCondition(InRecipeSearchRequestDTO.builder()
                        .searchWord(searchWord)
                        .recipeType(recipeType)
                        .recipeFoodType(recipeFoodType)
                        .category(category)
                        .difficulty(difficulty)
                        .score(score)
                        .build()),
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
    public InRecipeBasicListDTO<String> getConditionByRecipeType(){
        return new InRecipeBasicListDTO<>(findSearchConditionUseCase.findRecipeTypeCond());
    }

    @GetMapping("/api/recipe/search/condition/difficulty")
    public InRecipeBasicListDTO<String> getConditionByRecipeDifficulty(){
        return new InRecipeBasicListDTO<>(findSearchConditionUseCase.findRecipeDifficultyCond());
    }

}
