package refrigerator.back.recipe.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import refrigerator.back.recipe.application.domain.entity.RecipeScore;

@Data
@Builder
public class OutRecipeDetailDTO {
    private RecipeScore score;
    private Integer person;
    private Integer views;
    private Integer bookmarks;
    private String recipeFoodTypeName;
    private String recipeCategoryName;

    @QueryProjection
    public OutRecipeDetailDTO(RecipeScore score, Integer person, Integer views, Integer bookmarks, String recipeFoodTypeName, String recipeCategoryName) {
        this.score = score;
        this.person = person;
        this.views = views;
        this.bookmarks = bookmarks;
        this.recipeFoodTypeName = recipeFoodTypeName;
        this.recipeCategoryName = recipeCategoryName;
    }
}
