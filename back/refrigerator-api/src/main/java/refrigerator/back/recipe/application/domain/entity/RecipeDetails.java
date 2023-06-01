package refrigerator.back.recipe.application.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecipeDetails{
    private RecipeScore score;
    private Integer views;
    private Integer bookmarks;
    private String recipeFoodTypeName;
    private String recipeCategoryName;
}
