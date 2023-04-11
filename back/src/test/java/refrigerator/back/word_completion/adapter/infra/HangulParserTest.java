package refrigerator.back.word_completion.adapter.infra;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.back.word_completion.application.domain.RecipeNameParserUtils;

import java.text.Normalizer;

@SpringBootTest
class HangulParserTest {

    @Autowired RecipeNameParserUtils recipeNameParserUtils;

    @Test
    void 자모_분리(){
        String recipeName = "김치";
        String result = recipeNameParserUtils.split(recipeName);
        Assertions.assertThat(result).isEqualTo("ㄱㅣㅁㅊㅣ");
    }

}