package refrigerator.back.word_completion.application.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.back.word_completion.application.port.in.RecipeWordCompletionUseCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class RecipeWordCompletionServiceTest {

    @Autowired RecipeWordCompletionService service;

    @Test
    void 자동_완성() {
        String keyword = "당";
        long start = System.currentTimeMillis();
        List<String> result = service.search(keyword);
        long end = System.currentTimeMillis() - start;
        Assertions.assertThat(end < 10L).isTrue();
        for (String item : result) {
            Assertions.assertThat(item.startsWith(keyword)).isTrue();
        }
    }
}