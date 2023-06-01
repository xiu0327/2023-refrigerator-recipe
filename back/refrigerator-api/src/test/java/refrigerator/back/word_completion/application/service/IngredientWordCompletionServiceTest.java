package refrigerator.back.word_completion.application.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.back.word_completion.application.domain.WordParserUtilsImpl;
import refrigerator.back.word_completion.application.port.in.IngredientWordCompletionUseCase;

import java.util.List;

@SpringBootTest
@Slf4j
class IngredientWordCompletionServiceTest {

    @Autowired IngredientWordCompletionUseCase service;
    WordParserUtilsImpl wordParserUtils = new WordParserUtilsImpl();

    @Test
    @DisplayName("자음/모음 짝이 맞는 검색 단어가 들어왔을 때")
    void search() {
        String keyword = "아";
        String keywordSplit = wordParserUtils.split(keyword);
        List<String> result = service.search(keyword);
        for (String item : result) {
            String itemSplit = wordParserUtils.split(item);
            Assertions.assertThat(itemSplit.startsWith(keywordSplit)).isTrue();
        }
    }

    @Test
    @DisplayName("일치하는 단어가 없음에도 결과가 뜨는 버그 수정")
    void searchBug1() {
        String keyword = "콜";
        String keywordSplit = wordParserUtils.split(keyword);
        List<String> result = service.search(keyword);
        for (String item : result) {
            String itemSplit = wordParserUtils.split(item);
            Assertions.assertThat(itemSplit.startsWith(keywordSplit)).isTrue();
        }
    }

    @Test
    @DisplayName("자음/모음 짝이 맞지 않는 검색 단어가 들어왔을 때")
    void searchOneWord() {
        String keyword = "ㅇ";
        List<String> result = service.search(keyword);
        for (String item : result) {
            String itemSplit = wordParserUtils.split(item);
            Assertions.assertThat(itemSplit.startsWith(keyword)).isTrue();
        }
    }

    @Test
    @DisplayName("영어로 된 키워드 혹은 일치하는 결과가 없을 경우 빈 배열 반환")
    void searchIncorrectWord() {
        String keyword = "apple";
        List<String> result = service.search(keyword);
        Assertions.assertThat(result).isEmpty();
    }

}