package refrigerator.back.recipe.adapter.out.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static refrigerator.back.recipe.application.domain.entity.QRecipeBookmark.recipeBookmark;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.recipeScore;
import static refrigerator.back.recipe.application.domain.entity.QRecipeViews.recipeViews;

@Repository
@RequiredArgsConstructor
public class RecipeRepositoryImpl implements RecipeRepository{

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

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
    public void updateRecipeScore(Long recipeID, double score, int person) {
        jpaQueryFactory
                .update(recipeScore)
                .set(recipeScore.person, recipeScore.person.add(person))
                .set(recipeScore.score, recipeScore.score.add(score))
                .where(recipeScore.recipeID.eq(recipeID))
                .execute();
        em.flush();
        em.clear();
    }

    @Override
    public void updateBookmarkScore(Long recipeID, int value) {
        if (!(value == 1 || value == -1)){
            throw new RuntimeException("bookmark update value is 1 or -1.");
        }
        jpaQueryFactory
                .update(recipeBookmark)
                .set(recipeBookmark.count, recipeBookmark.count.add(value))
                .where(recipeBookmark.recipeID.eq(recipeID))
                .execute();
        em.flush();
        em.clear();
    }

}