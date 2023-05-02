package refrigerator.back.ingredient.adapter.out.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;

import java.util.List;

import static refrigerator.back.ingredient.application.domain.QRegisteredIngredient.registeredIngredient;

@Component
@RequiredArgsConstructor
public class RegisteredIngredientQuery {

    private final JPAQueryFactory jpaQueryFactory;

    public List<RegisteredIngredient> findIngredientList() {
        return jpaQueryFactory
                .selectFrom(registeredIngredient)
                .fetch();
    }
}
