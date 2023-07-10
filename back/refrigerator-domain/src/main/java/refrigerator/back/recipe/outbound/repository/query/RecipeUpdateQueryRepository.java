package refrigerator.back.recipe.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

import static refrigerator.back.recipe.application.domain.entity.QRecipeBookmark.recipeBookmark;
import static refrigerator.back.recipe.application.domain.entity.QRecipeScore.recipeScore;
import static refrigerator.back.recipe.application.domain.entity.QRecipeViews.recipeViews;


@Component
@RequiredArgsConstructor
public class RecipeUpdateQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public void updateRecipeViews(Long recipeID) {
        jpaQueryFactory
                .update(recipeViews)
                .set(recipeViews.views, recipeViews.views.add(1))
                .where(recipeViews.recipeID.eq(recipeID))
                .execute();
        em.flush();
        em.clear();
    }
    

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