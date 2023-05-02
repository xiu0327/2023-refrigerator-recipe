package refrigerator.back.ingredient.application.port.out;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.adapter.out.module.RegisteredIngredientInit;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.word_completion.application.port.out.FindRegisteredIngredientNameListPort;

import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
class FindRegisteredIngredientPortTest {

    @Autowired FindRegisteredIngredientPort findRegisteredIngredientPort;
    @Autowired FindRegisteredIngredientNameListPort findRegisteredIngredientNameListPort;

    @Test
    void 구현체_일치_확인(){
        // 특수한 경우가 아니면 두 port 의 구현체는 일치함
        Assertions.assertThat(findRegisteredIngredientPort.getClass()).isEqualTo(findRegisteredIngredientNameListPort.getClass());
    }

    @Test
    void 현재_구현체_확인(){
        Assertions.assertThat(findRegisteredIngredientPort.getClass()).isEqualTo(RegisteredIngredientInit.class);
        Assertions.assertThat(findRegisteredIngredientNameListPort.getClass()).isEqualTo(RegisteredIngredientInit.class);
    }

    @Test
    @DisplayName("findIngredientNameList")
    void findIngredientNameList_디버깅() {
        List<String> result = findRegisteredIngredientNameListPort.findIngredientNameList();
        log.info("result = " + result);
    }

    @Test
    @DisplayName("findIngredientList")
    void findIngredientList_디버깅() {
        List<RegisteredIngredient> result = findRegisteredIngredientPort.findIngredientList();
        log.info("result = " + result);
    }
}