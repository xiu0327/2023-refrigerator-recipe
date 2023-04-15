package refrigerator.back.recipe.adapter.out.repository.query;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeDTO;
import refrigerator.back.recipe.adapter.out.dto.QOutRecipeDTO;
import refrigerator.back.recipe.application.domain.entity.*;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;
import static refrigerator.back.recipe.application.domain.entity.QRecipeBookmark.recipeBookmark;
import static refrigerator.back.recipe.application.domain.entity.QRecipeCategory.*;
import static refrigerator.back.recipe.application.domain.entity.QRecipeFoodType.*;
import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.*;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.recipeScore;
import static refrigerator.back.recipe.application.domain.entity.QRecipeViews.recipeViews;

@Repository
@RequiredArgsConstructor
public class RecipeSearchQueryRepositoryImpl implements RecipeSearchQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OutRecipeDTO> searchRecipe(RecipeSearchCondition condition, Pageable pageable) {
        return jpaQueryFactory
                .select(new QOutRecipeDTO(
                        recipe.recipeID, recipe.recipeName, recipe.image,
                        recipeScore,
                        recipeViews.views))
                .from(recipe)
                .leftJoin(recipeScore).on(recipeScore.recipeID.eq(recipe.recipeID))
                .leftJoin(recipeViews).on(recipeViews.recipeID.eq(recipe.recipeID))
                .leftJoin(recipeBookmark).on(recipeBookmark.recipeID.eq(recipe.recipeID))
                .leftJoin(recipeFoodType).on(recipeFoodType.typeID.eq(recipe.recipeFoodType))
                .where(
                        recipeTypeEq(condition.getRecipeType()),
                        recipeDifficultyEq(condition.getDifficulty()),
                        recipeScoreGoe(condition.getScore()),
                        recipeFoodTypeEq(condition.getRecipeFoodType()),
                        isContain(condition.getSearchWord())
                )
                .orderBy(recipeViews.views.desc(), recipeBookmark.count.desc(), recipe.recipeName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression isContain(String searchWord){
        return isKeywordInRecipeIngredient(searchWord).or(recipeNameContains(searchWord));
    }

    private BooleanExpression isKeywordInRecipeIngredient(String searchWord) {
        return searchWord != null ? recipe.recipeID.in(JPAExpressions.select(recipeIngredient.recipeID)
                .from(recipeIngredient)
                .where(recipeIngredient.name.eq(searchWord))) : null;
    }

    private BooleanExpression recipeNameContains(String searchWord) {
        return searchWord != null ? recipe.recipeName.contains(searchWord) : null;
    }

    private BooleanExpression recipeScoreGoe(Double score) {
        return score != null ? recipeScore.score.goe(score) : null;
    }

    private BooleanExpression recipeDifficultyEq(String difficulty) {
        return hasText(difficulty) ? recipe.difficulty.eq(difficulty) : null;
    }

    private BooleanExpression recipeFoodTypeEq(String foodType) {
        return hasText(foodType) ? recipeFoodType.typeName.eq(foodType) : null;
    }

    private BooleanExpression recipeTypeEq(String recipeType) {
        return hasText(recipeType) ? recipe.recipeType.eq(recipeType) : null;
    }

    @Override
    public List<RecipeFoodType> findRecipeFoodTypeList() {
        return jpaQueryFactory
                .selectFrom(recipeFoodType)
                .fetch();
    }

    @Override
    public List<RecipeCategory> findRecipeCategoryList() {
        return jpaQueryFactory
                .selectFrom(recipeCategory)
                .fetch();
    }
}
