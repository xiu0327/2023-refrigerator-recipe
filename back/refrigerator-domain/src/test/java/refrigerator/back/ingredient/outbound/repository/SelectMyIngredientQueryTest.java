package refrigerator.back.ingredient.outbound.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.ingredient.outbound.dto.OutMyIngredientDto;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisabledRepositoryTest
@Import({QuerydslConfig.class, IngredientLookUpQueryRepository.class})
@TestDataInit("/ingredient.sql")
class SelectMyIngredientQueryTest {

    @Autowired IngredientLookUpQueryRepository queryRepository;

    @Test
    @DisplayName("유통기한 >= 현재날짜 and deleted = false and memberId = email 조건의 쿼리 테스트")
    void selectMyIngredients() {
        String memberId = "jktest101@gmail.com";
        LocalDate now = LocalDate.of(2023, 12, 24);
        LocalDate.of(2023, 12, 24);
        List<OutMyIngredientDto> result = queryRepository.selectMyIngredients(memberId, now);
        assertEquals(3, result.size());
    }
}