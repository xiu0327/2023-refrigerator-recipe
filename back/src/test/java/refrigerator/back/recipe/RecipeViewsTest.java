package refrigerator.back.recipe;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.adapter.out.entity.RecipeViews;
import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;
import refrigerator.back.recipe.application.port.in.FindRecipeDetailUseCase;
import refrigerator.back.recipe.application.port.out.AddRecipeViewsPort;
import refrigerator.back.recipe.application.port.out.ReadRecipePort;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RecipeViewsTest {

    @Autowired EntityManager entityManager;
    @Autowired AddRecipeViewsPort addRecipeViewsPort;
    @Autowired ReadRecipePort readRecipePort;
    @Autowired FindRecipeDetailUseCase findRecipeDetailUseCase;

    @Test
    @Transactional
    void 영속성_변경감지_테스트(){
        long recipeID = 1L;
        RecipeViews recipeViews1 = entityManager.find(RecipeViews.class, recipeID);
        int views = recipeViews1.getViews();
        int newViews = views + 1;
        RecipeViews recipeViews = entityManager.find(RecipeViews.class, recipeID);
        recipeViews.addViews();
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @Transactional
    void 조회수_증가_테스트(){
        Long recipeID = 1L;
        RecipeDomain firstSelectRecipe = readRecipePort.getRecipeDetails(recipeID);
        addRecipeViewsPort.addViews(recipeID);
        RecipeDomain secondSelectRecipe = readRecipePort.getRecipeDetails(recipeID);
        addRecipeViewsPort.addViews(recipeID);
        // 조회수가 증가 되었는지 확인
        assertThat(firstSelectRecipe.getViews() + 1).isEqualTo(secondSelectRecipe.getViews());
    }

    @Test
    void 조회수_증가_실패_테스트(){
        Long wrongRecipeID = 1L;
        int beforeViews = readRecipePort.getRecipeDetails(wrongRecipeID).getViews();
        // 레시피 조회 실패 시, 롤백되어 트랜잭션 원자성이 보장되는지 확인
        assertThrows(BusinessException.class, () -> {
            try{
                findRecipeDetailUseCase.getRecipe(wrongRecipeID, false);
            }catch (BusinessException e){
                RecipeDomain recipe = readRecipePort.getRecipeDetails(wrongRecipeID);
                assertThat(recipe.getViews()).isEqualTo(beforeViews); // 조회수가 변경되지 않아야 함
                throw e;
            }
        });

    }
}
