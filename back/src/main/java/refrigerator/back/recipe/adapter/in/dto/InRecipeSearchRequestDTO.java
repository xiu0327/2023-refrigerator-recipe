package refrigerator.back.recipe.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;



@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InRecipeSearchRequestDTO {
    @Nullable
    private String searchWord;
    @Nullable
    private String recipeType;
    @Nullable
    private String recipeFoodType;
    @Nullable
    private String category;
    @Nullable
    private String difficulty;
    @Nullable
    private Double score;
}
