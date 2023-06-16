package refrigerator.back.recipe.application.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.exception.domain.BusinessException;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import javax.persistence.*;

@Entity
@Table(name = "recipe_score")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeScore {

    @Id
    @Column(name = "recipe_id")
    private Long recipeID;

    @Column(name = "person", nullable = false)
    private int person;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "score_avg", nullable = false)
    private Double score;

    public void toCalculateTotalScore(double scoreValue){
        isCorrectScoreRange(scoreValue);
        this.person += 1;
        this.amount += scoreValue;
        this.score = this.person > 0 ? this.amount / this.person : 0.0;
    }

    public void toRecalculateTotalScore(double oldScore, double newScore){
        isCorrectScoreRange(newScore);
        this.amount += (newScore - oldScore);
        this.score = this.person > 0 ? this.amount / this.person : 0.0;
    }

    private void isCorrectScoreRange(double score) {
        if (score <= 0.0 || score > 5.0){
            throw new BusinessException(RecipeExceptionType.NOT_ACCEPTABLE_RANGE);
        }
    }

}
