package refrigerator.back.searchword.adapter.out;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.searchword.adapter.mapper.SearchWordMapper;
import refrigerator.back.searchword.adapter.out.dto.QOutIngredientDTO;
import refrigerator.back.searchword.application.domain.Ingredient;
import refrigerator.back.searchword.application.port.out.FindIngredientsByMemberPort;

import java.util.List;
import java.util.stream.Collectors;

import static refrigerator.back.ingredient.application.domain.QIngredient.*;

@Repository
@RequiredArgsConstructor
public class RecommendSearchWordAdapter implements FindIngredientsByMemberPort {

    private final JPAQueryFactory jpaQueryFactory;
    private final SearchWordMapper mapper;

    @Override
    public List<Ingredient> getIngredients(String memberId) {
        return jpaQueryFactory.select(new QOutIngredientDTO(ingredient.name, ingredient.expirationDate))
                .from(ingredient)
                .where(ingredient.email.eq(memberId))
                .fetch()
                .stream().map(mapper::toIngredient)
                .collect(Collectors.toList());
    }
}
