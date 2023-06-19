package refrigerator.server.api.recipe_search.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InRecipeSearchConditionDto {
    private String searchWord;
    private String recipeType;
    private String recipeFoodType;
    private String category;
    private String difficulty;
    private Double score;
}
