package refrigerator.back.recipe.adapter.out.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.application.domain.entity.RecipeScore;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RecipePersistenceRepository {

    private final EntityManager em;

    public Optional<RecipeScore> findRecipeScoreByRecipeId(Long recipeId){
        return em.createQuery("select s from RecipeScore s where s.recipeID= :recipeId", RecipeScore.class)
                .setParameter("recipeId", recipeId)
                .getResultList()
                .stream().findAny();
    }
}
