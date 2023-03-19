package refrigerator.back.recipe.adapter.out.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


import static refrigerator.back.recipe.adapter.out.entity.QRecipeScore.*;
import static refrigerator.back.recipe.adapter.out.entity.QRecipeViews.*;

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

}