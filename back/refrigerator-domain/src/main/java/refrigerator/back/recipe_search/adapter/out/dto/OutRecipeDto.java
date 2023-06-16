package refrigerator.back.recipe_search.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
@EqualsAndHashCode
public class OutRecipeDto implements Serializable {
    private Long recipeID;
    private String recipeName;
    private String image;
    private Double score;
    private Integer views;

    @QueryProjection
    public OutRecipeDto(Long recipeID, String recipeName, String image, Double score, Integer views) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.image = image;
        this.score = score;
        this.views = views;
    }
}
