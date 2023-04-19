package refrigerator.back.word_completion.application.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.back.word_completion.application.port.in.IngredientWordCompletionUseCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class IngredientWordCompletionServiceTest {

    @Autowired IngredientWordCompletionUseCase service;

    @Test
    void search() {
        String keyword = "닭";
        long start = System.currentTimeMillis();
        List<String> result = service.search(keyword);
        long end = System.currentTimeMillis() - start;
        Assertions.assertThat(end < 10L).isTrue();
        Assertions.assertThat("나물비빔밥").isNotIn(result);
        for (String item : result) {
            Assertions.assertThat(item.startsWith(keyword)).isTrue();
        }
    }
}