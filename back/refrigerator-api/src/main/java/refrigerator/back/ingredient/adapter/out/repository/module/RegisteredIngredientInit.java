package refrigerator.back.ingredient.adapter.out.repository.module;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.infra.redis.IngredientCacheKey;


import java.util.List;

import static refrigerator.back.ingredient.application.domain.QRegisteredIngredient.registeredIngredient;


@Component
@RequiredArgsConstructor
public class RegisteredIngredientInit {

    private final JPAQueryFactory jpaQueryFactory;

    @Cacheable(value = IngredientCacheKey.REGISTERED_INGREDIENT,
            key = "'registered_ingredient'",
            cacheManager = "registeredIngredientCacheManager")
    public List<RegisteredIngredient> getIngredientList() {
        return jpaQueryFactory
                .selectFrom(registeredIngredient)
                .fetch();
    }

}
