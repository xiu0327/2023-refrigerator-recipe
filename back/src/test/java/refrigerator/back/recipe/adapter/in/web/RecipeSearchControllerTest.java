package refrigerator.back.recipe.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.global.TestData;
import refrigerator.back.recipe.adapter.in.dto.InRecipeSearchRequestDTO;
import refrigerator.back.searchword.application.port.in.FindLastSearchWordUseCase;

import java.util.List;

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
    @Autowired TestData testData;
    @Autowired FindLastSearchWordUseCase findLastSearchWordUseCase;
    @Autowired CreateTokenPort createTokenPort;

    @Before
    public void setting(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("레시피 조회")
    void findById() throws Exception {
        String memberId = testData.createMemberByEmail("email@gmail.com");
        String token = createTokenPort.createTokenWithDuration(memberId, "ROLE_STEADY_STATUS", 3000);
        mockMvc.perform(get("/api/recipe/1")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("레시피 검색")
    void search() throws Exception {
        String memberId = testData.createMemberByEmail("email@gmail.com");
        String token = createTokenPort.createTokenWithDuration(memberId, "ROLE_STEADY_STATUS", 3000);
        mockMvc.perform(get("/api/recipe/search?searchWord=두부&page=0")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
        List<String> searchWords = findLastSearchWordUseCase.getLastSearchWords(memberId);
        Assertions.assertThat("두부").isIn(searchWords);
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