package refrigerator.back.recipe_searchword.transactiontest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.recipe_searchword.application.port.out.FindIngredientsByMemberPort;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class TransactionATest {

    @Autowired EntityManager em;
    @Autowired
    FindIngredientsByMemberPort findIngredientsByMemberPort;

    private final String email = "email124@gmail.com";
    private final String[] names = {"사과", "사과", "배", "소고기", "소고기"};
    private final String[] deletedNames = {"오이", "당근"};


    @BeforeEach
    void init(){
        log.info("before start");
        for (int day = 1 ; day <= names.length ; day++){
            Ingredient ingredient1 = Ingredient.create(
                    names[day - 1],
                    LocalDate.now().plusDays(day),
                    30.0,
                    "g",
                    IngredientStorageType.FREEZER,
                    day,
                    email);
            em.persist(ingredient1);
        }
        for (int day = 1 ; day <= deletedNames.length ; day++){
            Ingredient ingredient = Ingredient.create(
                    deletedNames[day - 1],
                    LocalDate.now().plusDays(day),
                    30.0,
                    "g",
                    IngredientStorageType.FREEZER,
                    day,
                    email);
            ingredient.delete();
            em.persist(ingredient);
        }
    }

    @Test
    void A(){
        log.info("======= Test A =======");
        List<String> result = findIngredientsByMemberPort.getIngredients(email);
        log.info("result = {}", result.size());
    }

    @Test
    void B(){
        log.info("======= Test B =======");
        List<String> result = findIngredientsByMemberPort.getIngredients(email);
        log.info("result = {}", result.size());
    }
}
