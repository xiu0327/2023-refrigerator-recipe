package refrigerator.back.ingredient.adapter.out.repository.module;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;

import javax.annotation.PostConstruct;
import java.util.List;

import static refrigerator.back.ingredient.application.domain.QRegisteredIngredient.registeredIngredient;

@Component
@RequiredArgsConstructor
public class RegisteredIngredientInit {

    private final JPAQueryFactory jpaQueryFactory;
    private List<RegisteredIngredient> ingredients;

    @PostConstruct
    public void init(){
        this.ingredients = jpaQueryFactory
                .selectFrom(registeredIngredient)
                .fetch();;
    }

    public List<RegisteredIngredient> getIngredientList() {
        return ingredients;
    }

}
