package refrigerator.back.myscore.application.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyRecipeScoreDomain {
    private Long scoreID;
    private String recipeName;
    private String recipeImage;
    private Double score;
    private LocalDateTime registrationDate;

    /* 비즈니스 로직 */
    public void modifyScore(double score){
        this.score = score;
    }
}
