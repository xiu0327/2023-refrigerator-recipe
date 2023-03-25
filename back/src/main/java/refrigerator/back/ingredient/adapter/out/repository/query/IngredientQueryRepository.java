package refrigerator.back.ingredient.adapter.out.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientResponseDTO;
import refrigerator.back.ingredient.adapter.out.dto.QOutIngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.out.dto.QOutIngredientResponseDTO;
import refrigerator.back.ingredient.exception.IngredientExceptionType;

import java.util.List;

import static org.springframework.util.StringUtils.*;
import static refrigerator.back.ingredient.application.domain.QIngredient.*;

@Repository
@RequiredArgsConstructor
public class IngredientQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<OutIngredientResponseDTO> findList(String storage, boolean deadline, String email) {
        return jpaQueryFactory
                .select(new QOutIngredientResponseDTO(
                        ingredient.id,
                        ingredient.name,
                        ingredient.registrationDate,
                        ingredient.expirationDate
                ))
                .from(ingredient)
                .where(storageCheck(storage), deadlineCheck(deadline), emailCheck(email))
                .fetch();

    }

    public List<OutIngredientResponseDTO> findSearchList(String name, String email) {
        return jpaQueryFactory
                .select(new QOutIngredientResponseDTO(
                        ingredient.id,
                        ingredient.name,
                        ingredient.registrationDate,
                        ingredient.expirationDate
                ))
                .from(ingredient)
                .where(nameCheck(name), emailCheck(email))
                .fetch();
    }

    public OutIngredientDetailResponseDTO findByIngredientId(Long id) {
        return jpaQueryFactory
                .select(new QOutIngredientDetailResponseDTO(
                        ingredient.id,
                        ingredient.name,
                        ingredient.registrationDate,
                        ingredient.storageMethod,
                        ingredient.expirationDate,
                        ingredient.capacity,
                        ingredient.capacityUnit,
                        ingredient.email
                ))
                .from(ingredient)
                .where(idCheck(id))
                .fetchOne();
    }

    private BooleanExpression idCheck(Long id){
        if(id != null)
            return ingredient.id.eq(id);
        else
            throw new BusinessException(IngredientExceptionType.TEST_ERROR);
    }

    private BooleanExpression emailCheck(String email) {
        if(hasText(email))
            return ingredient.email.eq(email);
        else
            throw new BusinessException(IngredientExceptionType.TEST_ERROR);
    }

    private BooleanExpression storageCheck(String storage) {
        return hasText(storage) ? ingredient.storageMethod.eq(storage) : null;
    }

    private BooleanExpression deadlineCheck(boolean deadline) {
        return deadline ? ingredient.registrationDate.gt(ingredient.expirationDate) : null;
        // 날짜 비교 리팩토링
        // 테스트할 때 log 찍어보기
    }

    private BooleanExpression nameCheck(String name) {
        return hasText(name) ? ingredient.name.contains(name) : null;
    }

}
