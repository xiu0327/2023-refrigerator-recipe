package refrigerator.back.recipe.adapter.out.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.dto.QRecipeListMappingDTO;
import refrigerator.back.recipe.adapter.out.dto.RecipeListMappingDTO;
import refrigerator.back.recipe.adapter.out.dto.RecipeSearchConditionDTO;
import refrigerator.back.recipe.adapter.out.entity.QRecipeCategory;
import refrigerator.back.recipe.adapter.out.entity.RecipeCategory;
import refrigerator.back.recipe.adapter.out.entity.RecipeFoodType;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static refrigerator.back.recipe.adapter.out.entity.QRecipe.recipe;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeBookmark.recipeBookmark;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeCategory.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeFoodType.recipeFoodType;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeScore.recipeScore;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeViews.recipeViews;

@Repository
@RequiredArgsConstructor
public class RecipeSearchQueryRepositoryImpl implements RecipeSearchQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RecipeListMappingDTO> searchRecipe(RecipeSearchConditionDTO condition, Pageable pageable) {
        return jpaQueryFactory
                .select(new QRecipeListMappingDTO(
                        recipe.recipeID, recipe.recipeName, recipe.image,
                        recipeScore.person, recipeScore.score,
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
                        recipeFoodTypeEq(condition.getRecipeFoodType())
                )
                .orderBy(recipeViews.views.desc(), recipeBookmark.count.desc(), recipe.recipeName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
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
