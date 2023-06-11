package refrigerator.back.recipe.adapter.in;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class RecipeDetailsControllerTest {

    @Autowired MockMvc mockMvc;


    @Test
    @DisplayName("레시피 조회")
    @WithUserDetails("nhtest@gmail.com")
    void findById() throws Exception {
        mockMvc.perform(get("/api/recipe/1/details")
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

}