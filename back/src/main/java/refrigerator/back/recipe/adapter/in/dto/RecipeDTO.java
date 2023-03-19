package refrigerator.back.recipe.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO implements Serializable {
    private Long recipeID;
    private String recipeName;
    private String image;
    private double scoreAvg;
    private Integer views;
}
