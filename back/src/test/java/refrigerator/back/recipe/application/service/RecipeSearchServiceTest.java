package refrigerator.back.recipe.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecipeSearchServiceTest {

    @Autowired RecipeSearchService searchService;

    @Test
    void 레시피_검색() {

    }
}