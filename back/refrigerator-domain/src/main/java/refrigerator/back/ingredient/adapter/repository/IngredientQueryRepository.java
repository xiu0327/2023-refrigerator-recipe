package refrigerator.back.ingredient.adapter.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.dto.OutIngredientDTO;
import refrigerator.back.ingredient.adapter.dto.OutIngredientDetailDTO;
import refrigerator.back.ingredient.adapter.dto.QOutIngredientDTO;
import refrigerator.back.ingredient.adapter.dto.QOutIngredientDetailDTO;
import refrigerator.back.ingredient.application.domain.*;
import refrigerator.back.ingredient.exception.IngredientExceptionType;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static refrigerator.back.ingredient.application.domain.QIngredient.ingredient;
import static refrigerator.back.ingredient.application.domain.QIngredientImage.ingredientImage;


@Component
@RequiredArgsConstructor
public class IngredientQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    /** set */

    public void deleteIngredient(Long id) {

        jpaQueryFactory.update(ingredient)
                .set(ingredient.deleted, true)
                .where(ingredient.id.eq(id))
                .execute();

        em.flush();
        em.clear();
    }

    public void deleteAllIngredients(List<Long> ids) {

        jpaQueryFactory.update(ingredient)
                .set(ingredient.deleted, true)
                .where(ingredient.id.in(ids))
                .execute();

        em.flush();
        em.clear();
    }

    /** get */

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

    /** condition */

    private BooleanBuilder emailCheck(String email) {
        return nullSafeBuilder(() -> ingredient.email.eq(email));
    }

    private BooleanBuilder storageCheck(IngredientStorageType storage) {
        return nullSafeBuilder(() -> ingredient.storageMethod.eq(storage));
    }

    private BooleanExpression deadlineCheck(boolean deadline) {
        return deadline ? ingredient.expirationDate.lt(LocalDate.now()) : ingredient.expirationDate.goe(LocalDate.now());
    }

    public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (NullPointerException e) {
            throw new BusinessException(IngredientExceptionType.NOT_FOUND_INGREDIENT);
        }
    }
}
