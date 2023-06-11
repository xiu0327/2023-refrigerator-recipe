package refrigerator.back.recipe_search.adapter.in;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class RecipeSearchConditionControllerTest {

    @Autowired MockMvc mockMvc;

    @Test
    @DisplayName("레시피 음식 유형 조회")
    void getConditionByFoodType() throws Exception {
        mockMvc.perform(get("/api/recipe/search/condition/food-type")
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("레시피 카테고리 조회")
    void getConditionByCategory() throws Exception {
        mockMvc.perform(get("/api/recipe/search/condition/category")
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("레시피 유형 조회")
    void getConditionByRecipeType() throws Exception {
        mockMvc.perform(get("/api/recipe/search/condition/recipe-type")
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("레시피 난이도 조회")
    void getConditionByRecipeDifficulty() throws Exception {
        mockMvc.perform(get("/api/recipe/search/condition/difficulty")
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

}