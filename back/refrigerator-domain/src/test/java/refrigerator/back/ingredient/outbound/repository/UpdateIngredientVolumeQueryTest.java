package refrigerator.back.ingredient.outbound.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.WriteQueryResultType;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.ingredient.application.domain.Ingredient;

import static org.junit.jupiter.api.Assertions.*;

@DisabledRepositoryTest
@Import({QuerydslConfig.class, IngredientUpdateQueryRepository.class})
@TestDataInit("/ingredient.sql")
class UpdateIngredientVolumeQueryTest {

    @Autowired IngredientUpdateQueryRepository queryRepository;
    @Autowired TestEntityManager em;

    @Test
    @DisplayName("식재료 용량 업데이트 쿼리")
    void updateIngredientVolume(){
        // given
        Long id = 1L;
        Double volume = 20.2;
        // when
        WriteQueryResultType result = queryRepository.updateIngredientVolume(1L, volume);
        // then
        assertEquals(WriteQueryResultType.NORMAL, result);
        Ingredient target = em.find(Ingredient.class, 1L);
        assertEquals(volume, target.getCapacity());
    }

}