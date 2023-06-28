package refrigerator.back.ingredient.adapter.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.ingredient.adapter.dto.OutRecipeIngredientDTO;
import refrigerator.back.ingredient.adapter.dto.QOutRecipeIngredientDTO;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.infra.redis.IngredientCacheKey;


import javax.persistence.EntityManager;
import java.util.List;

import static refrigerator.back.ingredient.application.domain.QRegisteredIngredient.registeredIngredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;

@Component
@RequiredArgsConstructor
public class IngredientQuerySubRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Cacheable(
            value = IngredientCacheKey.REGISTERED_INGREDIENT,
            key = "'registered_ingredient'",
            cacheManager = "registeredIngredientCacheManager"
    )
    public List<RegisteredIngredient> getIngredientList() {
        return jpaQueryFactory
                .selectFrom(registeredIngredient)
                .fetch();
    }

    public void saveSuggestIngredient(SuggestedIngredient ingredient) {
        em.persist(ingredient);
    }

    public List<OutRecipeIngredientDTO> getRecipeIngredient(Long recipeId) {
        return jpaQueryFactory
                .select(new QOutRecipeIngredientDTO(recipeIngredient.ingredientID, recipeIngredient.name))
                .from(recipeIngredient)
                .where(recipeIngredient.recipeID.eq(recipeId))
                .fetch();
    }
}
