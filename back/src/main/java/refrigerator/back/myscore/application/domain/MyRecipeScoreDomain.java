package refrigerator.back.myscore.application.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyRecipeScoreDomain {
    private Long scoreID;
    private String memberID;
    private Long recipeID;
    private String recipeName;
    private String recipeImage;
    private Double score;
    private LocalDateTime createDate;
    private Integer views;

    /* 비즈니스 로직 */
    public void modify(double score){
        this.score = score;
    }

}
