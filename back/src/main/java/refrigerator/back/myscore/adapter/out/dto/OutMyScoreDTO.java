package refrigerator.back.myscore.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import refrigerator.back.recipe.adapter.out.entity.RecipeScore;

@Getter
@Builder
public class OutMyScoreDTO {
    private Long scoreId;
    private Long recipeId;
    private String recipeImage;
    private String recipeName;
    private RecipeScore score;
    private Integer views;

    @QueryProjection
    public OutMyScoreDTO(Long scoreId, Long recipeId, String recipeImage, String recipeName, RecipeScore score, Integer views) {
        this.scoreId = scoreId;
        this.recipeId = recipeId;
        this.recipeImage = recipeImage;
        this.recipeName = recipeName;
        this.score = score;
        this.views = views;
    }
}
