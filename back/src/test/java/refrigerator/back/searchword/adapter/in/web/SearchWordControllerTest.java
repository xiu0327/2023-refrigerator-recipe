package refrigerator.back.searchword.adapter.in.web;

import org.junit.Before;
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
import refrigerator.back.searchword.application.port.in.AddSearchWordUseCase;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class SearchWordControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext context;
    @Autowired
    TestData testData;
    @Autowired CreateTokenPort createTokenPort;
    @Autowired AddSearchWordUseCase addSearchWordUseCase;

    @Before
    public void setting(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void 추천_검색어_조회() throws Exception {
        String memberId = testData.createMemberByEmail("email@gmail.com");
        testData.createIngredientWithDate("도라지", memberId, LocalDate.of(2023, 5, 1));
        testData.createIngredientWithDate("안심", memberId, LocalDate.of(2023, 5, 2));
        testData.createIngredientWithDate("호박", memberId, LocalDate.of(2023, 5, 3));
        testData.createIngredientWithDate("콩나물", memberId, LocalDate.of(2023, 5, 4));
        testData.createIngredientWithDate("돼지고기", memberId, LocalDate.of(2023, 5, 5));
        testData.createIngredientWithDate("부추", memberId, LocalDate.of(2023, 5, 6));
        String token = createTokenPort.createTokenWithDuration(memberId, "ROLE_STEADY_STATUS", 4000);
        mockMvc.perform(get("/api/search-word/recommend")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.data").isNotEmpty()
        ).andDo(print());

    }

    @Test
    void 최근_검색어_조회() throws Exception {
        String memberId = testData.createMemberByEmail("email@gmail.com");
        String token = createTokenPort.createTokenWithDuration(memberId, "ROLE_STEADY_STATUS", 4000);
        mockMvc.perform(get("/api/search-word/last")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.data").isNotEmpty()
        ).andDo(print());

    }

    @Test
    void 최근_검색어_삭제() throws Exception {
        String memberId = testData.createMemberByEmail("email@gmail.com");
        String word = "검색어2";
        String token = createTokenPort.createTokenWithDuration(memberId, "ROLE_STEADY_STATUS", 4000);
        mockMvc.perform(delete("/api/search-word?word=" + word)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }
}