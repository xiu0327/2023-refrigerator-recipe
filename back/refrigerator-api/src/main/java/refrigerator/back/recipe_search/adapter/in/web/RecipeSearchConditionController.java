package refrigerator.back.recipe_search.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.recipe_search.adapter.in.dto.InRecipeBasicListDTO;
import refrigerator.back.recipe_search.application.port.in.FindSearchConditionUseCase;

@RestController
@RequiredArgsConstructor
public class RecipeSearchConditionController {

    private final FindSearchConditionUseCase findSearchConditionUseCase;

    @GetMapping("/api/recipe/search/condition/food-type")
    public InRecipeBasicListDTO<String> getConditionByFoodType(){
        return new InRecipeBasicListDTO<>(findSearchConditionUseCase.findRecipeFoodTypeCond());
    }

    @GetMapping("/api/recipe/search/condition/category")
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
