package refrigerator.back.searchword.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.searchword.application.port.in.FindRecommendSearchWordUseCase;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class RecommendSearchWordServiceTest {

    @Autowired FindRecommendSearchWordUseCase findRecommendSearchWordUseCase;
    @Autowired TestData testData;

    @Test
    @DisplayName("추천 검색어 조회")
    void getRecommendSearchWords() {
        String memberId = testData.createMemberByEmail("email@gmail.com");
        testData.createIngredientWithDate("콩나물", memberId, LocalDate.of(2023, 4, 22));
        testData.createIngredientWithDate("도라지", memberId, LocalDate.of(2023, 8, 28));
        testData.createIngredientWithDate("계란", memberId, LocalDate.of(2023, 8, 29));
        testData.createIngredientWithDate("콩", memberId, LocalDate.of(2023, 8, 30));
        testData.createIngredientWithDate("돼지고기", memberId, LocalDate.of(2023, 9, 28));
        testData.createIngredientWithDate("당근", memberId, LocalDate.of(2023, 9, 29));
        testData.createIngredientWithDate("부추", memberId, LocalDate.of(2023, 9, 30));
        List<String> result = findRecommendSearchWordUseCase.getRecommendSearchWords(memberId);
        assertThat(result.size()).isEqualTo(5);
        assertThat(result.get(0)).isEqualTo("도라지");
        assertThat(result.get(1)).isEqualTo("계란");
        assertThat(result.get(2)).isEqualTo("콩");
        assertThat(result.get(3)).isEqualTo("돼지고기");
        assertThat(result.get(4)).isEqualTo("당근");
    }
}