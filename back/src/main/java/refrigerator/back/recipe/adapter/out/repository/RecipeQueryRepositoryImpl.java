package refrigerator.back.recipe.adapter.out.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.dto.*;
import refrigerator.back.recipe.adapter.out.entity.*;
import refrigerator.back.recipe.adapter.out.mapper.RecipeDTOMapper;

import java.util.List;

import static refrigerator.back.recipe.adapter.out.entity.QRecipe.recipe;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeBookmark.recipeBookmark;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeCategory.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeCourse.recipeCourse;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeFoodType.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeIngredient.recipeIngredient;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeScore.recipeScore;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeViews.recipeViews;

@Repository
@RequiredArgsConstructor
public class RecipeQueryRepositoryImpl implements RecipeQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final RecipeDTOMapper mapper;

    @Override
    public RecipeMappingDTO findRecipeByID(Long recipeID) {
        Recipe findRecipe = getRecipeData(recipeID);
        RecipeJoinDataMappingDTO findJoinData = getRecipeJoinData(recipeID);
        RecipeMappingDTO result = mapper.entityToDto(findRecipe, findJoinData);
        result.setIngredients(findRecipe.getIngredients());
        return result;
    }

    @Override
    public List<RecipeListMappingDTO> findRecipeList(Pageable pageable) {
        return jpaQueryFactory
                .select(new QRecipeListMappingDTO(
                        recipe.recipeID, recipe.recipeName, recipe.image,
                        recipeScore.person, recipeScore.score,
                        recipeBookmark.count))
                .from(recipe)
                .innerJoin(recipeScore).on(recipeScore.recipeID.eq(recipe.recipeID))
                .innerJoin(recipeViews).on(recipeViews.recipeID.eq(recipe.recipeID))
                .innerJoin(recipeBookmark).on(recipeViews.recipeID.eq(recipe.recipeID))
                .orderBy(recipeViews.views.desc(), recipeBookmark.count.desc(), recipe.recipeName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Recipe> searchRecipe(RecipeSearchConditionDTO recipeSearch) {
        return null;
    }

    @Override
    public List<RecipeCourse> findRecipeCourse(Long recipeID) {
        return jpaQueryFactory
                .select(recipeCourse)
                .from(recipeCourse)
                .where(recipeCourse.recipeID.eq(recipeID))
                .fetch();
    }

    private Recipe getRecipeData(Long recipeID) {
        return jpaQueryFactory
                .select(QRecipe.recipe)
                .from(QRecipe.recipe)
                .innerJoin(QRecipe.recipe.ingredients, recipeIngredient)
                .where(QRecipe.recipe.recipeID.eq(recipeID))
                .fetchJoin()
                .fetchOne();
    }

    private RecipeJoinDataMappingDTO getRecipeJoinData(Long recipeID){
        return jpaQueryFactory
                .select(new QRecipeJoinDataMappingDTO(
                        recipeScore.score,
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

}
