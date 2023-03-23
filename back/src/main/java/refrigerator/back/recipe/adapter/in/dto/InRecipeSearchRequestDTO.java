package refrigerator.back.recipe.adapter.in.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
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
