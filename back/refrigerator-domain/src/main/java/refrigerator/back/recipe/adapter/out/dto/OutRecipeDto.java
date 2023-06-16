package refrigerator.back.recipe.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import refrigerator.back.recipe.adapter.out.mapper.OutRecipeBasicDataMapper;
import refrigerator.back.recipe.application.domain.dto.RecipeDomainDto;

@Getter
@ToString
public class OutRecipeDto {
    Long recipeID;
    String recipeName;
    String image;
    Double scoreAvg;
    String description;
    Integer cookingTime;
    Integer kcal;
    Integer servings;
    String difficulty;

    @QueryProjection
    public OutRecipeDto(Long recipeID, String recipeName, String image, Double scoreAvg, String description, Integer cookingTime, Integer kcal, Integer servings, String difficulty) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.image = image;
        this.scoreAvg = scoreAvg;
        this.description = description;
        this.cookingTime = cookingTime;
        this.kcal = kcal;
        this.servings = servings;
        this.difficulty = difficulty;
    }

    public RecipeDomainDto mappingToDomainDto(OutRecipeBasicDataMapper mapper){
        return mapper.toRecipeDomainDto(this);
    }
}
