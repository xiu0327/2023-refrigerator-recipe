package refrigerator.back.word_completion.application.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WordFormatTest {

    @Test
    void inputCheck() {
        WordFormatValidation wordFormat = new WordFormatValidation();
        Assertions.assertThat(wordFormat.wordCheck("사과")).isTrue();
        Assertions.assertThat(wordFormat.wordCheck("apple")).isFalse();
    }
}