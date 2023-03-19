package refrigerator.back.recipe.adapter.out.dto;

import lombok.Builder;
import lombok.Data;
import refrigerator.back.recipe.adapter.out.entity.RecipeIngredient;

import java.util.Set;


@Data
@Builder
public class RecipeMappingDTO {

    private Long recipeID;
    private String recipeName;
    private String description;
    private Integer cookingTime;
    private Integer kcal;
    private Integer servings;
    private String difficulty;
    private String recipeType;
    private String image;
    private Double score;
    private Integer person;
    private Integer views;
    private Integer bookmarks;
    private String recipeFoodTypeName;
    private String recipeCategoryName;
    private Set<RecipeIngredient> ingredients;

}
