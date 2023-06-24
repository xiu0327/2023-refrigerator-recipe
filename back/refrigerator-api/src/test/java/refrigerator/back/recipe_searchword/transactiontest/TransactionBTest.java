package refrigerator.back.recipe_searchword.transactiontest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe_searchword.application.port.out.FindIngredientsByMemberPort;

import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class TransactionBTest {

    @Autowired
    FindIngredientsByMemberPort findIngredientsByMemberPort;

    private final String email = "email124@gmail.com";

    @Test
    void B(){
        log.info("======= Test B =======");
        List<String> result = findIngredientsByMemberPort.getIngredients(email);
        log.info("result = {}", result.size());
    }
}