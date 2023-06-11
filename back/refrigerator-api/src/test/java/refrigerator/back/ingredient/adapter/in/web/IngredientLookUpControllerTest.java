package refrigerator.back.ingredient.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.global.TestData;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IngredientLookUpControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired TestData testData;
    @Autowired CreateTokenPort createTokenPort;


    // RequestParam and PathValuable validation 방법 강구

    @Test
    void 식재료_이름에_따른_용량단위_반환() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        String name = "콩나물";

        mockMvc.perform(get("/api/ingredients/unit?name=" + name)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_이름에_따른_용량단위_반환_실패_등록되지_않은_식재료() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        String name = "파워에이드";

        mockMvc.perform(get("/api/ingredients/unit?name=" + name)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_목록_조회() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        testData.createIngredient("안심", email);

        String storage = "냉장";
        String deadline = "false";

        mockMvc.perform(get("/api/ingredients?storage="+storage+"&deadline="+deadline+"&page=0")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_목록_조회_실패_존재하지_않는_보관타입() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        testData.createIngredient("안심", email);

        String storage = "방관";
        String deadline = "false";

        mockMvc.perform(get("/api/ingredients?storage="+storage+"&deadline="+deadline+"&page=0")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    // 오류메시지가 없음 => RequestParam and PathValuable validation 방법 강구
    @Test
    void 식재료_목록_조회_실패_Boolean_타입_오류() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        testData.createIngredient("안심", email);

        String storage = "냉동";
        String deadline = "test";

        mockMvc.perform(get("/api/ingredients?storage="+storage+"&deadline="+deadline+"&page=0")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_검색() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        mockMvc.perform(get("/api/ingredients/search")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_단건_조회() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        Long id = testData.createIngredient("콩나물", email);

        mockMvc.perform(get("/api/ingredients/" + id)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_단건_조회_실패() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        mockMvc.perform(get("/api/ingredients/51651656165")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }
    
    @Test
    void 임박_식재료_목록_조회() throws Exception {

        String days = "1";

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 4000);

        mockMvc.perform(get("/api/ingredients/deadline/" + days)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }
}