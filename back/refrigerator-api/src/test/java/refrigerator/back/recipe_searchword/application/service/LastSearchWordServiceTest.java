package refrigerator.back.recipe_searchword.application.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.annotation.RedisFlushAll;
import refrigerator.back.global.TestData;

import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
@RedisFlushAll(beanName = "searchWordRedisTemplate")
class LastSearchWordServiceTest {

    @Autowired LastSearchWordService lastSearchWordService;
    @Autowired TestData testData;

    @Test
    @DisplayName("검색어 추가")
    void addSearchWord() {
        String memberId = testData.createMemberByEmail("email@gmail.com");
        String searchWord = "검색어";
        lastSearchWordService.addSearchWord(memberId, searchWord);
        List<String> result = lastSearchWordService.getLastSearchWords(memberId);
        String findSearchWord = result.get(0);
        Assertions.assertThat(findSearchWord).isEqualTo(searchWord);
        lastSearchWordService.delete(memberId, searchWord);
    }

    @Test
    @DisplayName("검색어 중복 제거 확인")
    void checkDuplication(){
        String memberId = testData.createMemberByEmail("email1234@gmail.com");
        String searchWord = "검색어";
        lastSearchWordService.addSearchWord(memberId, searchWord);
        lastSearchWordService.addSearchWord(memberId, searchWord);
        int size = lastSearchWordService.getLastSearchWords(memberId).size();
        Assertions.assertThat(size).isEqualTo(1);
        lastSearchWordService.delete(memberId, searchWord);
    }

    @Test
    @DisplayName("검색어 삭제")
    void delete() {
        String memberId = testData.createMemberByEmail("email45678@gmail.com");
        String searchWord = "검색어";
        lastSearchWordService.addSearchWord(memberId, searchWord);
        lastSearchWordService.delete(memberId, searchWord);
        List<String> result = lastSearchWordService.getLastSearchWords(memberId);
        Assertions.assertThat(searchWord).isNotIn(result);
    }

    @Test
    @DisplayName("최근 검색어 조회")
    void findSearchWord(){
        String memberId = testData.createMemberByEmail("email123432@gmail.com");
        lastSearchWordService.addSearchWord(memberId, "검색어1");
        lastSearchWordService.addSearchWord(memberId, "검색어2");
        List<String> result = lastSearchWordService.getLastSearchWords(memberId);
        Assertions.assertThat("검색어1").isEqualTo(result.get(1));
        Assertions.assertThat("검색어2").isEqualTo(result.get(0));
        lastSearchWordService.delete(memberId, "검색어1");
        lastSearchWordService.delete(memberId, "검색어2");
    }
}