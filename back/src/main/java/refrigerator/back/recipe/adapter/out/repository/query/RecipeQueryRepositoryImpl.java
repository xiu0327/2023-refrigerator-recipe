package refrigerator.back.recipe.adapter.out.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.dto.*;
import refrigerator.back.recipe.application.domain.entity.Recipe;
import refrigerator.back.recipe.application.domain.entity.RecipeCourse;

import java.util.List;

import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;
import static refrigerator.back.recipe.application.domain.entity.QRecipeBookmark.recipeBookmark;
import static refrigerator.back.recipe.application.domain.entity.QRecipeCategory.recipeCategory;
import static refrigerator.back.recipe.application.domain.entity.QRecipeCourse.recipeCourse;
import static refrigerator.back.recipe.application.domain.entity.QRecipeFoodType.recipeFoodType;
import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.recipeScore;
import static refrigerator.back.recipe.application.domain.entity.QRecipeViews.recipeViews;


@Repository
@RequiredArgsConstructor
public class RecipeQueryRepositoryImpl implements RecipeQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OutRecipeDTO> findRecipeList(Pageable pageable) {
        return jpaQueryFactory
                .select(new QOutRecipeDTO(
                        recipe.recipeID, recipe.recipeName, recipe.image,
                        recipeScore,
                        recipeViews.views))
                .from(recipe)
                .leftJoin(recipeScore).on(recipeScore.recipeID.eq(recipe.recipeID))
                .leftJoin(recipeViews).on(recipeViews.recipeID.eq(recipe.recipeID))
                .leftJoin(recipeBookmark).on(recipeBookmark.recipeID.eq(recipe.recipeID))
                .orderBy(recipeViews.views.desc(), recipeBookmark.count.desc(), recipe.recipeName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Recipe findRecipeById(Long recipeID) {
        return jpaQueryFactory
                .select(recipe)
                .from(recipe)
                .innerJoin(recipe.ingredients, recipeIngredient)
                .where(recipe.recipeID.eq(recipeID))
                .fetchJoin()
                .fetchOne();
    }

    @Override
    public OutRecipeDetailDTO findRecipeDetails(Long recipeID) {
        return jpaQueryFactory
                .select(new QOutRecipeDetailDTO(
                        recipeScore,
                        recipeScore.person,
                        recipeViews.views,
                        recipeBookmark.count,
                        recipeFoodType.typeName,
                        recipeCategory.categoryName))
                .from(recipe)
                .leftJoin(recipeBookmark).on(recipeBookmark.recipeID.eq(recipeID))
                .leftJoin(recipeViews).on(recipeViews.recipeID.eq(recipeID))
                .leftJoin(recipeScore).on(recipeScore.recipeID.eq(recipeID))
                .leftJoin(recipeCategory).on(recipeCategory.categoryID.eq(recipe.recipeCategory))
                .leftJoin(recipeFoodType).on(recipeFoodType.typeID.eq(recipe.recipeFoodType))
                .where(recipe.recipeID.eq(recipeID))
                .fetchOne();
    }

    @Override
    public List<RecipeCourse> findRecipeCourse(Long recipeID) {
        return jpaQueryFactory
                .select(recipeCourse)
                .from(recipeCourse)
                .where(recipeCourse.recipeID.eq(recipeID))
                .fetch();
    }

}
