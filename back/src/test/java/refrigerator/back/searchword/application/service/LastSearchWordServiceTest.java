package refrigerator.back.searchword.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.searchword.application.port.out.GetSearchWordListSizePort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LastSearchWordServiceTest {

    @Autowired LastSearchWordService lastSearchWordService;
    @Autowired GetSearchWordListSizePort getSearchWordListSizePort;
    @Autowired TestData testData;

    @Test
    @DisplayName("검색어 추가")
    void addSearchWord() {
        String memberId = testData.createMemberByEmail("email@gmail.com");
        lastSearchWordService.addSearchWord(memberId, "검색어");
        Long result = getSearchWordListSizePort.getWordListSize(memberId);
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("검색어 삭제")
    void delete() {
        String memberId = testData.createMemberByEmail("email@gmail.com");
        String searchWord = "검색어";
        lastSearchWordService.addSearchWord(memberId, searchWord);
        lastSearchWordService.delete(memberId, searchWord);
        Long result = getSearchWordListSizePort.getWordListSize(memberId);
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("최근 검색어 조회")
    void findSearchWord(){
        String memberId = testData.createMemberByEmail("email@gmail.com");
        lastSearchWordService.addSearchWord(memberId, "검색어1");
        lastSearchWordService.addSearchWord(memberId, "검색어2");
        List<String> result = lastSearchWordService.getLastSearchWords(memberId);
        Assertions.assertThat("검색어1").isIn(result);
        Assertions.assertThat("검색어2").isIn(result);
        Assertions.assertThat(result.size()).isEqualTo(2);
    }
}