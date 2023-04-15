package refrigerator.back.myscore.adapter.in.web;

import org.junit.Before;
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
import refrigerator.back.myscore.application.port.in.CookingUseCase;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MyRecipeScoreControllerTest {

    @Autowired TestData testData;
    @Autowired CreateTokenPort createTokenPort;
    @Autowired CookingUseCase cookingUseCase;
    @Autowired MockMvc mockMvc;
    @Autowired WebApplicationContext context;

    @Before
    public void setting(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void 요리_하기() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);
        mockMvc.perform(post("/api/my-score/cooking?recipeId=1&score=1.5")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(jsonPath("$.isCreated").isBoolean()
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 다시_요리_하기_실패() throws Exception {
        /* 0.1 ~ 0.5 사이의 별점이 아니면 에러 발생 */
        String email = testData.createMemberByEmail("email123@gmail.com");
        cookingUseCase.cooking(email, 1L, 1.5);
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);
        mockMvc.perform(post("/api/my-score/cooking?recipeId=1&score=7.5")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 요리_하기_실패() throws Exception {
        /* 0.1 ~ 0.5 사이의 별점이 아니면 에러 발생 */
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);
        mockMvc.perform(post("/api/my-score/cooking?recipeId=1&score=5.6")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 나의_별점_목록() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        cookingUseCase.cooking(email, 1L,  1.5);
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);
        mockMvc.perform(get("/api/my-score?page=0&size=11")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(jsonPath("$.scores").isNotEmpty()
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 별점_미리보기() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        cookingUseCase.cooking(email, 1L,  1.5);
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);
        mockMvc.perform(get("/api/my-score/preview?size=5")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(jsonPath("$.scores").isNotEmpty()
        ).andExpect(jsonPath("$.count").isNumber()
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 별점_수정() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        Long scoreId = cookingUseCase.cooking(email, 1L, 1.5).getScoreID();
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);
        mockMvc.perform(put("/api/my-score?scoreId=" + scoreId + "&score=3.0")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 별점_수정_실패() throws Exception {
        /* 0.1 ~ 0.5 사이의 별점이 아니면 에러 발생 */
        String email = testData.createMemberByEmail("email123@gmail.com");
        Long scoreId = cookingUseCase.cooking(email, 1L, 1.5).getScoreID();
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);
        mockMvc.perform(put("/api/my-score?scoreId=" + scoreId + "&score=0.0")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }
}