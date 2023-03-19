package refrigerator.back.recipe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.back.recipe.adapter.out.dto.QRecipeJoinDataMappingDTO;
import refrigerator.back.recipe.adapter.out.dto.RecipeMappingDTO;
import refrigerator.back.recipe.adapter.out.dto.RecipeJoinDataMappingDTO;
import refrigerator.back.recipe.adapter.out.entity.QRecipe;
import refrigerator.back.recipe.adapter.out.entity.Recipe;
import refrigerator.back.recipe.adapter.out.mapper.RecipeDTOMapper;

import static refrigerator.back.recipe.adapter.out.entity.QRecipe.recipe;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeBookmark.recipeBookmark;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeCategory.recipeCategory;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeFoodType.recipeFoodType;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeIngredient.recipeIngredient;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeScore.recipeScore;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeViews.recipeViews;

@SpringBootTest
@Slf4j
public class RecipeQueryTest {

    @Autowired JPAQueryFactory jpaQueryFactory;
    @Autowired RecipeDTOMapper mapper;

    @Test
    void 테스트(){
        Long recipeID = 1L;

        Recipe findRecipe = jpaQueryFactory
                .select(QRecipe.recipe)
                .from(QRecipe.recipe)
                .leftJoin(QRecipe.recipe.ingredients, recipeIngredient)
                .where(QRecipe.recipe.recipeID.eq(recipeID))
                .fetchJoin()
                .fetchOne();

        RecipeJoinDataMappingDTO result = jpaQueryFactory
                .select(new QRecipeJoinDataMappingDTO(
                        recipeScore.score,
                        recipeScore.person,
                        recipeViews.views,
                        recipeBookmark.count,
                        recipeFoodType.typeName,
                        recipeCategory.categoryName))
                .from(recipe)
                .innerJoin(recipeBookmark).on(recipeBookmark.recipeID.eq(recipeID))
                .innerJoin(recipeViews).on(recipeViews.recipeID.eq(recipeID))
                .innerJoin(recipeScore).on(recipeScore.recipeID.eq(recipeID))
                .innerJoin(recipeCategory).on(recipeCategory.categoryID.eq(recipe.recipeCategory))
                .innerJoin(recipeFoodType).on(recipeFoodType.typeID.eq(recipe.recipeFoodType))
                .where(recipe.recipeID.eq(recipeID))
                .fetchOne();

        RecipeMappingDTO findDto = mapper.entityToDto(findRecipe, result);
        log.info("dto = {}", findDto);
        System.out.println("RecipeQueryTest.테스트");
    }
}
