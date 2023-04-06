package refrigerator.back.recipe.adapter.in.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InRecipeDTO implements Serializable {
    private Long recipeID;
    private String recipeName;
    private String image;
    private Double scoreAvg;
    private Integer views;
}
