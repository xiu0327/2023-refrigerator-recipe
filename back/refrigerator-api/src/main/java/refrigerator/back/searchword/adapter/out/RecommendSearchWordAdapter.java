package refrigerator.back.searchword.adapter.out;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import refrigerator.back.searchword.adapter.out.dto.OutIngredientDTO;
import refrigerator.back.searchword.adapter.out.dto.QOutIngredientDTO;
import refrigerator.back.searchword.application.port.out.FindIngredientsByMemberPort;
import refrigerator.back.searchword.infra.SearchWordRedisKey;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static refrigerator.back.ingredient.application.domain.QIngredient.ingredient;


@Repository
@RequiredArgsConstructor
public class RecommendSearchWordAdapter implements FindIngredientsByMemberPort {

    private final JPAQueryFactory jpaQueryFactory;

    @Cacheable(
            value = SearchWordRedisKey.RECOMMEND_SEARCH_WORD,
            key = "#memberId",
            cacheManager = "searchWordsCacheManager"
    )
    @Override
    public List<String> getIngredients(String memberId) {
        return jpaQueryFactory
                .select(new QOutIngredientDTO(
                        ingredient.name,
                        ingredient.expirationDate))
                .from(ingredient)
                .where(
                        ingredient.email.eq(memberId),
                        ingredient.expirationDate.goe(LocalDate.now()),
                        ingredient.deleted.eq(false))
                .orderBy(ingredient.expirationDate.asc())
                .fetch()
                .stream().map(OutIngredientDTO::getName)
                .distinct()
                .limit(5)
                .collect(Collectors.toList());
    }
}
