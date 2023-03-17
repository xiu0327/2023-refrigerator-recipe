package refrigerator.back.recipe.application.domain.entity;

import lombok.Builder;
import lombok.Getter;
import refrigerator.back.recipe.application.domain.value.RecipeDifficulty;

import java.util.Set;

@Getter
@Builder
public class RecipeDomain {

    private Long recipeID;
    private String recipeName;
    private String description;
    private Integer cookingTime;
    private Integer kcal;
    private Integer servings;
    private RecipeDifficulty difficulty;
    private String recipeType;
    private String recipeFoodType;
    private String recipeCategory;
    private String image;
    private Integer person;
    private Integer score;
    private Integer views;
    private Integer bookmarks;
    private Double scoreAvg;
    private Set<RecipeIngredientDomain> ingredients;

    /* 비즈니스 로직 */
    public void initIngredient(Set<RecipeIngredientDomain> ingredients){
        this.ingredients = ingredients;
    }

    public void calculateScoreAvg(){
        if (person > 0){
            this.scoreAvg = (double) score / person;
        }
        else{
            this.scoreAvg = 0.0;
        }
    }

}
