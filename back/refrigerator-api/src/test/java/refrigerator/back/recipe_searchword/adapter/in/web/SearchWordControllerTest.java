package refrigerator.back.recipe_searchword.adapter.in.web;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.global.TestData;
import refrigerator.back.recipe_searchword.application.port.in.AddSearchWordUseCase;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class SearchWordControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TestData testData;
    @Autowired CreateTokenPort createTokenPort;
    @Autowired AddSearchWordUseCase addSearchWordUseCase;

    @Test
    void 추천_검색어_조회() throws Exception {
        String memberId = testData.createMemberByEmail("email@gmail.com");
        testData.createIngredientWithDate("도라지", memberId, LocalDate.of(2028, 5, 1));
        testData.createIngredientWithDate("안심", memberId, LocalDate.of(2028, 5, 2));
        testData.createIngredientWithDate("호박", memberId, LocalDate.of(2028, 5, 3));
        testData.createIngredientWithDate("콩나물", memberId, LocalDate.of(2028, 5, 4));
        testData.createIngredientWithDate("돼지고기", memberId, LocalDate.of(2028, 5, 5));
        testData.createIngredientWithDate("부추", memberId, LocalDate.of(2028, 5, 6));
        String token = createTokenPort.createTokenWithDuration(memberId, "ROLE_STEADY_STATUS", 4000);
        mockMvc.perform(get("/api/search-word/recommend")
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.data").isNotEmpty()
        ).andDo(print());

    }

    @Test
    void 최근_검색어_조회() throws Exception {
        String memberId = testData.createMemberByEmail("email12345@gmail.com");
        String token = createTokenPort.createTokenWithDuration(memberId, "ROLE_STEADY_STATUS", 4000);
        addSearchWordUseCase.addSearchWord(memberId, "감자");
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
        addSearchWordUseCase.addSearchWord(memberId, word);
        String token = createTokenPort.createTokenWithDuration(memberId, "ROLE_STEADY_STATUS", 4000);
        mockMvc.perform(delete("/api/search-word?word=" + word)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }
}