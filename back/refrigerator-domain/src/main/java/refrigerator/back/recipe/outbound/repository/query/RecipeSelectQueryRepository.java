package refrigerator.back.recipe.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import refrigerator.back.recipe.application.domain.entity.RecipeCourse;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredient;
import refrigerator.back.recipe.infra.redis.config.RecipeCacheKey;
import refrigerator.back.recipe.outbound.dto.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static refrigerator.back.ingredient.application.domain.QIngredient.ingredient;
import static refrigerator.back.mybookmark.application.domain.QMyBookmark.myBookmark;

import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;
import static refrigerator.back.recipe.application.domain.entity.QRecipeCourse.recipeCourse;
import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.recipeScore;

/**
 * 레시피 도메인과 관련된 쿼리문을 모아둔 Repository
 */
@Component
@RequiredArgsConstructor
public class RecipeSelectQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<OutMyIngredientDto> selectMyIngredients(String memberId){
        return queryFactory.select(new QOutMyIngredientDto(
                        ingredient.name,
                        ingredient.capacity))
                .from(ingredient)
                .where(ingredient.email.eq(memberId), ingredient.deleted.isFalse())
                .fetch();
    }

    public OutRecipeDto selectRecipeBasics(Long recipeId){
        // TODO : fetchOne()을 사용할 경우, 2개 이상의 결과가 나오면 NoUnique 에러가 발생 -> 에러 처리
        return queryFactory
                .select(new QOutRecipeDto(
                        recipe.recipeID,
                        recipe.recipeName,
                        recipe.image,
                        recipeScore.scoreAvg,
                        recipe.description,
                        recipe.cookingTime,
                        recipe.kcal,
                        recipe.servings,
                        recipe.difficulty))
                .from(recipe)
                .leftJoin(recipeScore).on(recipeScore.recipeId.eq(recipe.recipeID))
                .where(recipe.recipeID.eq(recipeId))
                .fetchOne();
    }

    public Long selectBookmarkByMemberId(Long recipeId, String memberId){
        return queryFactory.select(myBookmark.bookmarkId)
                .from(myBookmark)
                .where(myBookmark.memberId.eq(memberId),
                        myBookmark.recipeId.eq(recipeId),
                        myBookmark.deleted.eq(false))
                .fetchOne();
    }

    @Cacheable(value = RecipeCacheKey.RECIPE_INGREDIENT_AND_COURSE, key = "#recipeId", cacheManager = "recipeIngredientAndCourse")
    public OutRecipeOtherDto selectRecipeIngredientAndCourse(Long recipeId){
        Set<RecipeIngredient> ingredients = new HashSet<>();
        Set<RecipeCourse> courses = new HashSet<>();
        queryFactory.select(recipeIngredient, recipeCourse)
                .from(recipe)
                .innerJoin(recipeIngredient).on(recipeIngredient.recipeID.eq(recipe.recipeID))
                .innerJoin(recipeCourse).on(recipeCourse.recipeId.eq(recipe.recipeID))
                .where(recipe.recipeID.eq(recipeId))
                .fetch()
                .forEach(tuple -> {
                    ingredients.add(tuple.get(recipeIngredient));
                    courses.add(tuple.get(recipeCourse));
                });
        return new OutRecipeOtherDto(ingredients, courses);
    }
}
