package refrigerator.back.searchword.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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


    // TODO : 테스트 구현
    @DisplayName("중복된 식재료 / 삭제된 식재료를 제외하고 추천 검색어 추출")
    void getRecommendWordDuplication(){

    }

    @Test
    @DisplayName("DB에 데이터를 넣어뒀을 때 -> 추천 검색어 조회 테스트")
    void getRecommendSearchWordsUseDbData(){
        String memberId = "mstest102@gmail.com";
        List<String> result = findRecommendSearchWordUseCase.getRecommendSearchWords(memberId);
        log.info("result = {}", result.toString());
    }
}