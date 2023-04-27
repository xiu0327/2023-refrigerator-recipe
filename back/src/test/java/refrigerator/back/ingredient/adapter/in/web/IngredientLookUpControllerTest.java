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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IngredientLookUpControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired WebApplicationContext context;
    @Autowired TestData testData;
    @Autowired CreateTokenPort createTokenPort;

    // 수정..!!

    @BeforeEach
    public void setting(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        // 사전 식재료 등록 코드
    }

    @Test
    void 식재료_목록_조회() throws Exception {

        String content = "{\"storage\" : \"냉장\""
                + " , \"deadline\" : true}";

        String email = testData.createMemberByEmail("asd123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);

        mockMvc.perform(post("/api/ingredients?page=1&size=12")
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_목록_조회_실패() throws Exception {

        String content = "{\"storage\" : \"방관\""
                + " , \"deadline\" : true}";

        String email = testData.createMemberByEmail("asd123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);

        mockMvc.perform(post("/api/ingredients?page=1&size=12")
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_검색() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);

        String name = "감자";

        mockMvc.perform(post("/api/ingredients/search")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_검색_실패() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);

        String name = "감자";

        mockMvc.perform(post("/api/ingredients/search")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 등록된_식재료_목록_조회() throws Exception {
        mockMvc.perform(get("/api/ingredients/registered")
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 등록된_식재료_목록_조회_실패() throws Exception {
        mockMvc.perform(get("/api/ingredients/registered")
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_단건_조회() throws Exception {

        String id = "1";

        mockMvc.perform(get("api/ingredients/" + id)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_단건_조회_실패() throws Exception {

        String id = "1";

        mockMvc.perform(get("api/ingredients/" + id)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }
    
    @Test
    void 임박_식재료_목록_조회() throws Exception {

        String days = "1";

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);

        mockMvc.perform(get("api/ingredients/deadline/" + days)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 임박_식재료_목록_조회_실패() throws Exception {

        String days = "1";

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);

        mockMvc.perform(get("api/ingredients/deadline/" + days)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }
}