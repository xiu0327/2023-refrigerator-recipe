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
    private int cookingTime;
    private int kcal;
    private int servings;
    private RecipeDifficulty difficulty;
    private String recipeType;
    private String recipeFoodType;
    private String recipeCategory;
    private String image;
    private int person;
    private int score;
    private int views;
    private int bookmarks;
    private double scoreAvg = 0.0;
    private Set<RecipeIngredientDomain> ingredients;

    /* 비즈니스 로직 */
    public void initIngredient(Set<RecipeIngredientDomain> ingredients){
        this.ingredients = ingredients;
    }

    public void calculateScoreAvg(){
        if (person > 0){
            scoreAvg = (double) score / person;
        }
    }

}
