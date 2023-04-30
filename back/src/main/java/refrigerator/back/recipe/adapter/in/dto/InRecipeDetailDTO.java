package refrigerator.back.recipe.adapter.in.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import refrigerator.back.recipe.application.domain.entity.RecipeOutputFormat;

import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class InRecipeDetailDTO extends RecipeOutputFormat {
    private Long recipeID;
    private String recipeName;
    private String description;
    private String cookingTime;
    private String kcal;
    private String servings;
    private String difficulty;
    private String image;
    private Double scoreAvg;
    private Integer views;
    private Integer bookmarks;
    private String recipeFoodTypeName;
    private String recipeCategoryName;
    private Set<InRecipeIngredientDTO> ingredients;

    public void settingFormat(String servings, String kcal, String cookingTime){
        this.servings = servings;
        this.kcal = kcal;
        this.cookingTime = cookingTime;
    }

}
