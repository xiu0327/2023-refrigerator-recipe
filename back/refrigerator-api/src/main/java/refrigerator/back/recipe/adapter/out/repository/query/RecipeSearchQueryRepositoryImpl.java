package refrigerator.back.recipe.adapter.out.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import refrigerator.back.recipe.adapter.out.dto.OutRecipeDTO;
import refrigerator.back.recipe.adapter.out.dto.QOutRecipeDTO;
import refrigerator.back.recipe.application.domain.entity.RecipeCategory;
import refrigerator.back.recipe.application.domain.entity.RecipeFoodType;
import refrigerator.back.recipe.application.domain.entity.RecipeSearchCondition;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;
import static refrigerator.back.recipe.application.domain.entity.QRecipeBookmark.recipeBookmark;
import static refrigerator.back.recipe.application.domain.entity.QRecipeCategory.recipeCategory;
import static refrigerator.back.recipe.application.domain.entity.QRecipeFoodType.recipeFoodType;
import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;
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
                .leftJoin(recipeCategory).on(recipeCategory.categoryID.eq(recipe.recipeCategory))
                .where(
                        recipeTypeEq(condition.getRecipeType()),
                        recipeDifficultyEq(condition.getDifficulty()),
                        recipeScoreGoe(condition.getScore()),
                        recipeFoodTypeEq(condition.getRecipeFoodType()),
                        recipeCategoryEq(condition.getCategory()),
                        isContain(condition.getSearchWord())
                )
                .orderBy(recipeViews.views.desc(), recipeBookmark.count.desc(), recipe.recipeName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression recipeCategoryEq(String category) {
        if (category != null){
            if (hasText(category)){
                return recipeCategory.categoryName.eq(category);
            }
        }
        return null;
    }

    private BooleanExpression isContain(String searchWord){
        if (searchWord != null){
            if (hasText(searchWord)){
                return isKeywordInRecipeIngredient(searchWord).or(recipeNameContains(searchWord));
            }
        }
        return null;
    }

    private BooleanExpression isKeywordInRecipeIngredient(String searchWord) {
        return searchWord != null ? recipe.recipeID.in(JPAExpressions.select(recipeIngredient.recipeID)
                .from(recipeIngredient)
                .where(recipeIngredient.name.eq(searchWord))) : null;
    }

    private BooleanExpression recipeNameContains(String searchWord) {
        return recipe.recipeName.contains(searchWord);
    }

    private BooleanExpression recipeScoreGoe(Double score) {
        return score != null ? recipeScore.score.goe(score) : null;
    }

    private BooleanExpression recipeDifficultyEq(String difficulty) {
        if (difficulty != null){
            if (hasText(difficulty)){
                return recipe.difficulty.eq(difficulty);
            }
        }
        return null;
    }

    private BooleanExpression recipeFoodTypeEq(String foodType) {
        if (foodType != null){
            if (hasText(foodType)){
                return recipeFoodType.typeName.eq(foodType);
            }
        }
        return null;
    }

    private BooleanExpression recipeTypeEq(String recipeType) {
        if (recipeType != null){
            if (hasText(recipeType)){
                return recipe.recipeType.eq(recipeType);
            }
        }
        return null;
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
