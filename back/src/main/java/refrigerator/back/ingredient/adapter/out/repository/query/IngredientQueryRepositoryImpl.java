package refrigerator.back.ingredient.adapter.out.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.exception.IngredientExceptionType;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.*;
import static refrigerator.back.ingredient.application.domain.QIngredient.*;
import static refrigerator.back.ingredient.application.domain.QRegisteredIngredient.*;

@Repository
@RequiredArgsConstructor
public class IngredientQueryRepositoryImpl implements IngredientQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    public List<RegisteredIngredient> findRegisteredIngredient() {
        return jpaQueryFactory
                .selectFrom(registeredIngredient)
                .fetch();
    }

    @Override
    public List<Ingredient> findIngredientList(IngredientSearchCondition condition, Pageable pageable) {
        NumberExpression<Integer> rankPath = new CaseBuilder()
                .when(ingredient.expirationDate.goe(ingredient.registrationDate)).then(2)
                .otherwise(1);

        return jpaQueryFactory
                .selectFrom(ingredient)
                .where(
                        storageCheck(condition.getStorage()),
                        deadlineCheck(condition.isDeadline()),
                        emailCheck(condition.getEmail())
                )
                .orderBy(rankPath.desc(), ingredient.expirationDate.asc(), ingredient.name.asc()) // 이어서 코딩
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public void saveSuggestIngredient(SuggestedIngredient ingredient) {
        em.persist(ingredient);
    }

    private BooleanExpression emailCheck(String email) {
        if(hasText(email))
            return ingredient.email.eq(email);
        else
            throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);
    }

    private BooleanExpression storageCheck(String storage) {
        if(hasText(storage))
            return ingredient.storageMethod.eq(storage);
        else
            throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);
    }

    private BooleanExpression deadlineCheck(boolean deadline) {
        return deadline ? ingredient.registrationDate.gt(ingredient.expirationDate) : null;
    }
}
