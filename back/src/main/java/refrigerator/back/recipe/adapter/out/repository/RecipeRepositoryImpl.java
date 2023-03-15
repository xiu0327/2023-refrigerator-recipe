package refrigerator.back.recipe.adapter.out.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.entity.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import static refrigerator.back.recipe.adapter.out.entity.QRecipe.recipe;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeBookmark.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeCourse.recipeCourse;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeIngredient.recipeIngredient;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeScore.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeViews.*;

@Repository
@RequiredArgsConstructor
public class RecipeRepositoryImpl implements RecipeRepository{

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void save(Object entity) {
        em.persist(entity);
    }

    @Override
    public Recipe findRecipeByID(Long recipeID) {
        List<Tuple> recipeInfo = jpaQueryFactory
                .select(recipe,
                        recipeScore.score, recipeScore.person,
                        recipeViews.views,
                        recipeIngredient.ingredientID, recipeIngredient.volume, recipeIngredient.name, recipeIngredient.type,
                        recipeBookmark.count)
                .from(recipe)
                .innerJoin(recipeIngredient).on(recipeIngredient.recipeID.eq(recipeID))
                .innerJoin(recipeBookmark).on(recipeBookmark.recipeID.eq(recipeID))
                .innerJoin(recipeViews).on(recipeViews.recipeID.eq(recipeID))
                .innerJoin(recipeScore).on(recipeScore.recipeID.eq(recipeID))
                .where(recipe.recipeID.eq(recipeID))
                .fetch();
        return recipeMappingByID(recipeInfo);
    }

    private Recipe recipeMappingByID(List<Tuple> recipeInfo) {
        if (recipeInfo.size() < 1){
            throw new RuntimeException();
        }
        Recipe findRecipe = recipeInfo.get(0).get(recipe);
        if (findRecipe == null){
            throw new RuntimeException();
        }
        Integer score = recipeInfo.get(0).get(recipeScore.score);
        Integer person = recipeInfo.get(0).get(recipeScore.person);
        Integer views = recipeInfo.get(0).get(recipeViews.views);
        Integer bookmarks = recipeInfo.get(0).get(recipeBookmark.count);
        findRecipe.init(setRecipeIngredient(recipeInfo), score, person, views, bookmarks);
        return findRecipe;
    }

    private HashSet<RecipeIngredient> setRecipeIngredient(List<Tuple> recipeInfo) {
        HashSet<RecipeIngredient> result = new HashSet<>();
        for (Tuple tuple : recipeInfo) {
            result.add(RecipeIngredient.builder()
                    .ingredientID(tuple.get(recipeIngredient.ingredientID))
                    .name(tuple.get(recipeIngredient.name))
                    .volume(tuple.get(recipeIngredient.volume))
                    .type(tuple.get(recipeIngredient.type)).build());
        }
        return result;
    }

    @Override
    public List<Recipe> findRecipeList(Pageable pageable) {
        List<Tuple> recipeInfo = jpaQueryFactory
                .select(recipe.recipeID, recipe.recipeName, recipe.image,
                        recipeScore.person, recipeScore.score,
                        recipeBookmark.count)
                .from(recipe)
                .innerJoin(recipeScore).on(recipeScore.recipeID.eq(recipe.recipeID))
                .innerJoin(recipeViews).on(recipeViews.recipeID.eq(recipe.recipeID))
                .innerJoin(recipeBookmark).on(recipeViews.recipeID.eq(recipe.recipeID))
                .orderBy(recipeViews.views.desc(), recipeBookmark.count.desc(), recipe.recipeName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return recipeListMapping(recipeInfo);
    }

    private List<Recipe> recipeListMapping(List<Tuple> recipeInfo) {
        if (recipeInfo.size() < 1){
            throw new RuntimeException();
        }
        List<Recipe> result = new ArrayList<>();
        for (Tuple tuple : recipeInfo) {
            result.add(Recipe.builder()
                    .recipeID(tuple.get(recipe.recipeID))
                    .recipeName(tuple.get(recipe.recipeName))
                    .image(tuple.get(recipe.image))
                    .score(tuple.get(recipeScore.score))
                    .person(tuple.get(recipeScore.person))
                    .bookmarks(tuple.get(recipeBookmark.count))
                    .build());
        }
        return result;
    }

    @Override
    public List<Recipe> searchRecipe(RecipeSearch recipeSearch) {
        return null;
    }

    @Override
    public void updateRecipeViews(Long recipeID) {
        jpaQueryFactory
                .update(recipeViews)
                .set(recipeViews.views, recipeViews.views.add(1))
                .where(recipeViews.recipeID.eq(recipeID))
                .execute();
        em.flush();
        em.clear();
    }

    @Override
    public void updateRecipeScore(Long recipeID, double score) {
        jpaQueryFactory
                .update(recipeScore)
                .set(recipeScore.person, recipeScore.person.add(1))
                .set(recipeScore.score, recipeScore.score.add(score))
                .where(recipeScore.recipeID.eq(recipeID))
                .execute();
        em.flush();
        em.clear();
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
