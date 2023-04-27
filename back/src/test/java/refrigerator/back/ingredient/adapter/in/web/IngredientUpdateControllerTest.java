package refrigerator.back.ingredient.adapter.in.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.global.TestData;
import refrigerator.back.ingredient.application.service.IngredientUpdateService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Transactional
class IngredientUpdateControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired WebApplicationContext context;
    @Autowired TestData testData;
    @Autowired CreateTokenPort createTokenPort;
    @Autowired IngredientUpdateService ingredientUpdateService;

    private Long ingredientId = null;

    // 수정..!!

    @BeforeEach
    public void setting(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        ingredientId = ingredientUpdateService.registerIngredient("토마토", LocalDate.of(2023, 8, 8),
                30.0, "g", "냉장", 3, "asd123@gmail.com");
    }

    @Test
    void 식재료_등록() throws Exception {

        String content = "{ \"name\" : \"감자\" "+
                " \"expirationDate\" : \"2023-08-08T00:00:00\" "+       // 수정
                " \"capacity\" : 30.0 " +
                " \"capacityUnit\" : \"g\" " +
                " \"storageMethod\" : \"실온\" " +
                " \"imageId\" : 1 }";
        
        String email = testData.createMemberByEmail("asd123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);

        mockMvc.perform(post("/api/ingredients")
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_등록_실패() throws Exception {

        String content = "{ \"name\" : \"감자\" "+
                " \"expirationDate\" : \"2023-08-08T00:00:00\" "+       // 수정
                " \"capacity\" : 30.0 " +
                " \"capacityUnit\" : \"g\" " +
                " \"storageMethod\" : \"실온\" " +
                " \"imageId\" : 1 }";

        String email = testData.createMemberByEmail("asd123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);

        mockMvc.perform(post("/api/ingredients")
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_수정() throws Exception {

        mockMvc.perform(put("/api/ingredients/" + ingredientId)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_수정_실패() throws Exception {

        String id = "1";

        mockMvc.perform(put("/api/ingredients/" + ingredientId)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_삭제() throws Exception {

        String id = "1";

        mockMvc.perform(delete("/api/ingredients/" + ingredientId)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_삭제_실패() throws Exception {

        String id = "1";

        mockMvc.perform(delete("/api/ingredients/" + ingredientId)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }
}