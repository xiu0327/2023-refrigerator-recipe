package refrigerator.back.word_completion.application.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordFormatTest {

    @Test
    void inputCheck() {
        WordFormat wordFormat = new WordFormat();
        Assertions.assertThat(wordFormat.wordCheck("사과")).isTrue();
        Assertions.assertThat(wordFormat.wordCheck("apple")).isFalse();
    }
}