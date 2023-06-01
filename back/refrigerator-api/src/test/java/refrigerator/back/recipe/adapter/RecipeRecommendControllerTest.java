package refrigerator.back.recipe.adapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class RecipeRecommendControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired CreateTokenPort createTokenPort;


    @Test
    @DisplayName("레시피 추천")
    void recommend() throws Exception {
        String email = "mstest102@gmail.com";
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 7000);
        mockMvc.perform(get("/api/recipe/recommend")
                .header(HttpHeaders.AUTHORIZATION, makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.data").isArray()
        ).andDo(print());
    }

    private String makeTokenHeader(String token){
        return "Bearer " + token;
    }
}