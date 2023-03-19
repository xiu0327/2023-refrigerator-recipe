package refrigerator.back.recipe.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class RecipeListMappingDTO {
    private Long recipeID;
    private String recipeName;
    private String image;
    private Integer person;
    private Double score;
    private Integer views;

    @QueryProjection
    public RecipeListMappingDTO(Long recipeID, String recipeName, String image, Integer person, Double score, Integer views) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.image = image;
        this.person = person;
        this.score = score;
        this.views = views;
    }
}
