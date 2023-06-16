package refrigerator.back.ingredient.adapter.out.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientDTO;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientDetailDTO;
import refrigerator.back.ingredient.adapter.out.dto.QOutIngredientDTO;
import refrigerator.back.ingredient.adapter.out.dto.QOutIngredientDetailDTO;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.exception.IngredientExceptionType;


import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.*;
import static refrigerator.back.ingredient.application.domain.QIngredient.ingredient;
import static refrigerator.back.ingredient.application.domain.QIngredientImage.ingredientImage;

@Repository
@RequiredArgsConstructor
public class IngredientQueryRepositoryImpl implements IngredientQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    public void deleteIngredient(Long id) {
        jpaQueryFactory.update(ingredient)
                .set(ingredient.deleted, true)
                .where(ingredient.id.eq(id))
                .execute();

        em.flush();
        em.clear();
    }

    @Override
    public void deleteAllIngredients(List<Long> ids) {

        jpaQueryFactory.update(ingredient)
                .set(ingredient.deleted, true)
                .where(ingredient.id.in(ids))
                .execute();

        em.flush();
        em.clear();
    }

    @Override
    public void saveSuggestIngredient(SuggestedIngredient ingredient) {
        em.persist(ingredient);
    }

    @Override
    public List<OutIngredientDTO> findIngredientList(IngredientSearchCondition condition, Pageable pageable) {
        NumberExpression<Integer> rankPath = new CaseBuilder()
                .when(ingredient.expirationDate.goe(LocalDate.now())).then(2)
                .otherwise(1);

        return jpaQueryFactory
                .select(new QOutIngredientDTO(
                        ingredient.id,
                        ingredient.name,
                        ingredient.expirationDate,
                        ingredientImage.imageFileName))
                .from(ingredient)
                .where(
                        storageCheck(condition.getStorage()),
                        deadlineCheck(condition.getDeadline()),
                        emailCheck(condition.getEmail()),
                        ingredient.deleted.eq(false)
                )
                .leftJoin(ingredientImage).on(ingredientImage.id.eq(ingredient.image))
                .orderBy(rankPath.desc(), ingredient.expirationDate.asc(), ingredient.name.asc()) // 이어서 코딩
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<OutIngredientDTO> findIngredientListByDeadline(LocalDate expirationDate, String email) {
        return jpaQueryFactory
                .select(new QOutIngredientDTO(
                        ingredient.id,
                        ingredient.name,
                        ingredient.expirationDate,
                        ingredientImage.imageFileName))
                .from(ingredient)
                .where(
                        emailCheck(email),
                        ingredient.expirationDate.eq(expirationDate),
                        ingredient.deleted.eq(false)
                )
                .leftJoin(ingredientImage).on(ingredientImage.id.eq(ingredient.image))
                .fetch();
    }

    @Override
    public List<OutIngredientDTO> findIngredientListOfAll(String email) {
        return jpaQueryFactory
                .select(new QOutIngredientDTO(
                        ingredient.id,
                        ingredient.name,
                        ingredient.expirationDate,
                        ingredientImage.imageFileName))
                .from(ingredient)
                .where(
                        emailCheck(email),
                        ingredient.deleted.eq(false)
                )
                .leftJoin(ingredientImage).on(ingredientImage.id.eq(ingredient.image))
                .orderBy(ingredient.name.asc())
                .fetch();
    }

    @Override
    public Optional<OutIngredientDetailDTO> findIngredient(Long id) {
        OutIngredientDetailDTO outIngredientDetailDTO = jpaQueryFactory
                .select(new QOutIngredientDetailDTO(
                        ingredient.id,
                        ingredient.name,
                        ingredient.expirationDate,
                        ingredient.registrationDate,
                        ingredient.capacity,
                        ingredient.capacityUnit,
                        ingredient.storageMethod,
                        ingredientImage.imageFileName
                ))
                .from(ingredient)
                .where(
                        ingredient.id.eq(id),
                        ingredient.deleted.eq(false)
                )
                .leftJoin(ingredientImage).on(ingredientImage.id.eq(ingredient.image))
                .fetchOne();

        return Optional.ofNullable(outIngredientDetailDTO);
    }

    // condition

    private BooleanExpression emailCheck(String email) {
        if(hasText(email))
            return ingredient.email.eq(email);
        else
            throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);
    }

    private BooleanExpression storageCheck(IngredientStorageType storage) {
        if(storage != null)
            return ingredient.storageMethod.eq(storage);
        else
            throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);
    }

    private BooleanExpression deadlineCheck(boolean deadline) {
        return deadline ? ingredient.expirationDate.lt(LocalDate.now()) : null;
    }
}
