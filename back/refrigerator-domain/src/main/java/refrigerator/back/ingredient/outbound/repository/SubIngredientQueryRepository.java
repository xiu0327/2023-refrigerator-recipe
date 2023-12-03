package refrigerator.back.ingredient.outbound.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.QRegisteredIngredient;
import refrigerator.back.ingredient.outbound.dto.OutIngredientInRecipeDTO;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.infra.redis.IngredientCacheKey;
import refrigerator.back.ingredient.outbound.dto.QOutIngredientInRecipeDTO;


import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static refrigerator.back.ingredient.application.domain.QRegisteredIngredient.registeredIngredient;
import static refrigerator.back.ingredient.application.domain.QSuggestedIngredient.*;
import static refrigerator.back.recipe.application.domain.entity.QRecipeIngredient.recipeIngredient;

@Component
@RequiredArgsConstructor
public class SubIngredientQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    /** registeredIngredient **/

    @Cacheable(
            value = IngredientCacheKey.REGISTERED_INGREDIENT,
            key = "'registered_ingredient'",
            cacheManager = "registeredIngredientCacheManager"
    )
    public List<RegisteredIngredient> getRegisteredIngredientList() {
        return jpaQueryFactory
                .selectFrom(registeredIngredient)
                .fetch();
    }

    /** suggestedIngredient **/

    public Long saveSuggestIngredient(SuggestedIngredient ingredient) {
        em.persist(ingredient);
        return ingredient.getId();
    }

    @Transactional
    public Long deleteSuggestedIngredient(String name) {

        long execute = jpaQueryFactory.delete(suggestedIngredient)
                .where(suggestedIngredient.name.eq(name))
                .execute();

        em.flush();
        em.clear();

        return execute;
    }

    /** recipeIngredient **/

    public List<OutIngredientInRecipeDTO> getRecipeIngredient(Long recipeId) {
        return jpaQueryFactory
                .select(new QOutIngredientInRecipeDTO(recipeIngredient.ingredientId, recipeIngredient.name))
                .from(recipeIngredient)
                .where(recipeIngredient.recipeId.eq(recipeId))
                .fetch();
    }

    public Long saveRegisteredIngredient(RegisteredIngredient ingredient) {
        em.persist(ingredient);

        em.flush();
        em.clear();

        return ingredient.getId();
    }

    public Optional<String> findUnitName(String name) {

        List<String> result = jpaQueryFactory.select(suggestedIngredient.unit)
                .from(suggestedIngredient)
                .where(suggestedIngredient.name.eq(name))
                .groupBy(suggestedIngredient.unit)
                .orderBy(suggestedIngredient.unit.count().desc())
                .limit(1)
                .fetch();

        return Optional.ofNullable(result.get(0));
    }

    public List<SuggestedIngredient> testIngredient() {
        return jpaQueryFactory.selectFrom(suggestedIngredient)
                .fetch();
    }
}
