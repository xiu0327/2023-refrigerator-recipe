package refrigerator.back.ingredient.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.global.TestData;
import refrigerator.back.ingredient.adapter.in.dto.IngredientLookUpRequestDTO;

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

    @Before
    public void setting(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void 식재료_목록_조회() throws Exception {

        String email = testData.createMemberByEmail("asd123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        testData.createIngredient("안심", email);

        IngredientLookUpRequestDTO request = IngredientLookUpRequestDTO.builder()
                .storage("냉장")
                .deadline(true).build();

        String content = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(get("/api/ingredients?page=0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_목록_조회_실패() throws Exception {

        String email = testData.createMemberByEmail("asd123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientLookUpRequestDTO request = IngredientLookUpRequestDTO.builder()
                .storage("방관")
                .deadline(true).build();

        String content = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(get("/api/ingredients?page=0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
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
    void 임박_식재료_목록_조회() throws Exception {

        String days = "1";

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 1000);

        mockMvc.perform(get("/api/ingredients/deadline/" + days)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }
}