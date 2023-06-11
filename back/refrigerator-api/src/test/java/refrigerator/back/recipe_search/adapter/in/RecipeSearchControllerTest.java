package refrigerator.back.recipe_search.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe_search.adapter.in.dto.InRecipeSearchConditionDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class RecipeSearchControllerTest {

    @Autowired MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("레시피 검색")
    @WithUserDetails("nhtest@gmail.com")
    void search() throws Exception {
        InRecipeSearchConditionDto condition = InRecipeSearchConditionDto.builder()
                .searchWord("감자")
                .build();
        String json = objectMapper.writeValueAsString(condition);
        mockMvc.perform(post("/api/recipe/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("레시피 목록 조회")
    @WithUserDetails("nhtest@gmail.com")
    void normalSearch() throws Exception {
        mockMvc.perform(get("/api/recipe/search/normal?page=2&size=15")
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

}