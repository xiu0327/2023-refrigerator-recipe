package refrigerator.back.ingredient.adapter.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.QSuggestedIngredient;

import javax.persistence.EntityManager;
import java.util.List;

import static refrigerator.back.ingredient.application.domain.QIngredient.ingredient;

@Component
@RequiredArgsConstructor
public class IngredientUpdateQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    public Long updateIngredientDeleteStateTrue(Long id) {

        long execute = jpaQueryFactory.update(ingredient)
                .set(ingredient.deleted, true)
                .where(ingredient.id.eq(id))
                .execute();

        em.flush();
        em.clear();

        return execute;
    }

    public Long updateAllIngredientDeleteStateTrue(List<Long> ids) {

        long execute = jpaQueryFactory.update(ingredient)
                .set(ingredient.deleted, true)
                .where(ingredient.id.in(ids))
                .execute();

        em.flush();
        em.clear();

        return execute;
    }

    @Transactional
    public Long deleteIngredients() {

        long execute = jpaQueryFactory.delete(ingredient)
                .where(ingredient.deleted.eq(true))
                .execute();

        em.flush();
        em.clear();

        return execute;
    }
}
