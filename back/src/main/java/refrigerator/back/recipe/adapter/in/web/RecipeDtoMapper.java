package refrigerator.back.recipe.adapter.in.web;

import org.springframework.stereotype.Component;
import refrigerator.back.recipe.adapter.in.dto.*;
import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredientDomain;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RecipeDtoMapper {

    public RecipeCourseDtoList recipeCourseListMapper(List<RecipeCourseDomain> course){
        List<RecipeCourseDTO> list = course.stream()
                .map(this::recipeCourseMapper)
                .collect(Collectors.toList());
        return new RecipeCourseDtoList(list);
    }

    public RecipeDetailDTO recipeDetailMapper(RecipeDomain domain){
        return RecipeDetailDTO.builder()
                .recipeID(domain.getRecipeID())
                .recipeName(domain.getRecipeName())
                .description(domain.getDescription())
                .difficulty(domain.getDifficulty().getLevelName())
                .image(domain.getImage())
                .scoreAvg(domain.getScoreAvg())
                .kcal(RecipeDetailDTO.changeKcalFormat(domain.getKcal()))
                .cookingTime(RecipeDetailDTO.changeCookingTimeFormat(domain.getCookingTime()))
                .servings(RecipeDetailDTO.changeServingsFormat(domain.getServings()))
                .views(domain.getViews())
                .ingredients(recipeIngredientListMapper(domain.getIngredients()))
                .build();
    }

    public RecipeListDTO recipeListMapper(List<RecipeDomain> recipes){
        List<RecipeDTO> result = recipes.stream()
                .map(this::recipeMapper)
                .collect(Collectors.toList());
        return new RecipeListDTO(result);
    }

    private RecipeCourseDTO recipeCourseMapper(RecipeCourseDomain domain){
        return RecipeCourseDTO.builder()
                .step(String.format("%02d", domain.getStep()))
                .explanation(domain.getExplanation())
                .image(domain.getImage()).build();
    }

    private RecipeDTO recipeMapper(RecipeDomain domain){
        return RecipeDTO.builder()
                .recipeID(domain.getRecipeID())
                .recipeName(domain.getRecipeName())
                .scoreAvg(domain.getScoreAvg())
                .image(domain.getImage()).build();
    }

    private RecipeIngredientDTO recipeIngredientMapper(RecipeIngredientDomain domain){
        return RecipeIngredientDTO.builder()
                .ingredientID(domain.getIngredientID())
                .name(domain.getName())
                .volume(domain.getVolume())
                .type(domain.getType().getTypeName())
                .build();
    }

    private Set<RecipeIngredientDTO> recipeIngredientListMapper(Set<RecipeIngredientDomain> ingredients){
        if (ingredients == null){
            return null;
        }
        return ingredients.stream()
                .map(this::recipeIngredientMapper)
                .collect(Collectors.toSet());
    }
}
