package refrigerator.back.recipe.application.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.application.domain.entity.RecipeScore;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import static org.junit.jupiter.api.Assertions.*;

class RecipeScoreUnitTest {

    @Test
    @DisplayName("점수 합산 로직 테스트")
    void toCalculateTotalScore() {
        RecipeScore score = new RecipeScore(1L, 0, 0.0, 0.0);
        score.toCalculateTotalScore(3.2);
        score.toCalculateTotalScore(4.2);
        Assertions.assertTrue(score.getScore() > 0.0 && score.getScore() <= 5.0);
    }

    @Test
    @DisplayName("점수 다시 계산 테스트")
    void toRecalculateTotalScore(){
        RecipeScore score = new RecipeScore(1L, 1, 3.2, 3.2);
        score.toRecalculateTotalScore(3.2, 4.5);
        Assertions.assertEquals(score.getScore(), 4.5);
        Assertions.assertEquals(score.getAmount(), 4.5);
    }

    @Test
    @DisplayName("범위를 벗어난 점수를 저장하려고 할 때 -> 에러 발생")
    void fail(){
        RecipeScore score = new RecipeScore(1L, 0, 0.0, 0.0);
        assertThrows(BusinessException.class, () -> {
            try{
                score.toCalculateTotalScore(6.2);
            } catch (BusinessException e){
                assertEquals(e.getBasicExceptionType(), RecipeExceptionType.NOT_ACCEPTABLE_RANGE);
                throw e;
            }
        });
    }

    @Test
    @DisplayName("점수 저장 동시성 이슈 테스트")
    void calculateSameTime() throws InterruptedException {
        RecipeScore score = new RecipeScore(1L, 0, 0.0, 0.0);
        Thread threadA = new Thread(() -> {
            score.toCalculateTotalScore(2.3);
            try {
                Thread.sleep(1000); // 1초 지연
                System.out.println("threadA score = " + score.getScore());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread threadB = new Thread(() -> {
            score.toCalculateTotalScore(4.2);
            System.out.println("threadB score = " + score.getScore());
        });

        threadA.start();
        threadB.start();

        Thread.sleep(1000 * 3);
        assertEquals(score.getScore(), 3.25);

    }
}