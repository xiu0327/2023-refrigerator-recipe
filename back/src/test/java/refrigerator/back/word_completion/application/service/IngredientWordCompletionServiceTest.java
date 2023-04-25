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

import static org.junit.jupiter.api.Assertions.*;

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
        long start = System.currentTimeMillis();
        List<String> result = service.search(keyword);
        long end = System.currentTimeMillis() - start;
        Assertions.assertThat(end < 10L).isTrue();
        Assertions.assertThat("나물비빔밥").isNotIn(result);
        for (String item : result) {
            String itemSplit = wordParserUtils.split(item);
            Assertions.assertThat(itemSplit.startsWith(keywordSplit)).isTrue();
        }
    }

    @Test
    @DisplayName("자음/모음 짝이 맞지 않는 검색 단어가 들어왔을 때")
    void searchOneWord() {
        String keyword = "ㅇ";
        long start = System.currentTimeMillis();
        List<String> result = service.search(keyword);
        long end = System.currentTimeMillis() - start;
        Assertions.assertThat(end < 10L).isTrue();
        Assertions.assertThat("나물비빔밥").isNotIn(result);
        for (String item : result) {
            String itemSplit = wordParserUtils.split(item);
            Assertions.assertThat(itemSplit.startsWith(keyword)).isTrue();
        }
    }
}