package refrigerator.back.searchword.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.searchword.application.port.in.FindRecommendSearchWordUseCase;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecommendSearchWordServiceTest {

    @Autowired FindRecommendSearchWordUseCase findRecommendSearchWordUseCase;
    @Autowired TestData testData;

    @Test
    @DisplayName("추천 검색어 조회")
    void getRecommendSearchWords() {

    }
}