package refrigerator.back.myscore.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class MyRecipeScoreMappingDTO {
    private Long scoreID;
    private Long recipeID;
    private String recipeName;
    private String recipeImage;
    private Double score;
    private Integer views;
    private LocalDateTime createDate;

    @QueryProjection

    public MyRecipeScoreMappingDTO(Long scoreID, Long recipeID, String recipeName, String recipeImage, Double score, Integer views, LocalDateTime createDate) {
        this.scoreID = scoreID;
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeImage = recipeImage;
        this.score = score;
        this.views = views;
        this.createDate = createDate;
    }
}
