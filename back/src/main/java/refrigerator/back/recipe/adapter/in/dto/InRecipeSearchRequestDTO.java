package refrigerator.back.recipe.adapter.in.dto;

import lombok.Builder;

import lombok.Getter;
import org.springframework.lang.Nullable;


@Getter
@Builder
public class InRecipeSearchRequestDTO {
    @Nullable
    private String searchWord;
    @Nullable
    private String recipeType;
    @Nullable
    private String recipeFoodType;
    @Nullable
    private String difficulty;
    @Nullable
    private Double score;
}
