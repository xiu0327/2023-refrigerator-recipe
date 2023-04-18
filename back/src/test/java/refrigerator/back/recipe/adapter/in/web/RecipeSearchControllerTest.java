package refrigerator.back.recipe.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import refrigerator.back.recipe.adapter.in.dto.InRecipeSearchRequestDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class RecipeSearchControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired WebApplicationContext context;

    @Before
    public void setting(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("레시피 검색")
    void search() throws Exception {
        InRecipeSearchRequestDTO request = InRecipeSearchRequestDTO.builder()
                .searchWord("두부").build();
        String requestJson = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(get("/api/recipe/search?page=0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

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