package refrigerator.back.recipe.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDto;
import refrigerator.back.recipe.application.port.in.FindRecipeDetailsUseCase;

@SpringBootTest
@Transactional
class RecipeDetailsServiceTest {

    @Autowired FindRecipeDetailsUseCase useCase;

    @Test
    @DisplayName("레시피 상세 조회 통합테스트")
    void findRecipeOne() {
        InRecipeDto result = useCase.findRecipeDetails(1L, "mstest102@gmail.com");
        Assertions.assertTrue(result.isNotNull());
    }

}