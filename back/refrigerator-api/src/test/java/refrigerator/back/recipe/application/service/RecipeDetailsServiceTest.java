package refrigerator.back.recipe.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.application.domain.dto.RecipeDto;
import refrigerator.back.recipe.application.domain.entity.RecipeViews;
import refrigerator.back.recipe.application.port.in.FindRecipeDetailsUseCase;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class RecipeDetailsServiceTest {

    @Autowired FindRecipeDetailsUseCase useCase;
    @Autowired EntityManager em;

    @Test
    @DisplayName("레시피 상세 조회 통합테스트")
    void findRecipeOne() {
        long recipeId = 2L;
        RecipeDto result = useCase.findRecipeDetails(recipeId, "mstest102@gmail.com", false);
        RecipeDto nullObject = RecipeDto.builder().build();
        /* 1. 레시피 정보가 제대로 조회 되었는지 */
        Assertions.assertNotSame(nullObject, result);
        /* 2. 해당 레시피의 조회수가 1 증가 되었는지 */
        Assertions.assertEquals(1, em.find(RecipeViews.class, recipeId).getViews());
    }

    @Test
    @DisplayName("레시피 조회수 증가 동시성 이슈 여부 확인")
    void checkCoincidence() throws InterruptedException {
        long recipeId = 1L;

        AtomicInteger threadAView = new AtomicInteger();
        AtomicInteger threadBView = new AtomicInteger();

        Thread threadA = new Thread(() -> {
            useCase.findRecipeDetails(recipeId, "mstest102@gmail.com", false);
            threadAView.set(em.find(RecipeViews.class, recipeId).getViews());
        });
        Thread threadB = new Thread(() -> {
            useCase.findRecipeDetails(recipeId, "mstest102@gmail.com", false);
            threadBView.set(em.find(RecipeViews.class, recipeId).getViews());
        });

        threadA.start();
        threadB.start();

        Thread.sleep(1000);

        /* 실패할 경우, 동시성 이슈가 해결되지 않았음을 의미 */
        assertThat(threadAView.get()).isNotEqualTo(threadBView.get());
        Assertions.assertEquals(1, Math.abs(threadAView.get() - threadBView.get()));

    }

}