package refrigerator.back.ingredient.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import refrigerator.back.ingredient.adapter.in.dto.IngredientListRemoveRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientLookUpRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientRegisterRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientUpdateRequestDTO;
import refrigerator.back.ingredient.application.service.IngredientUpdateService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // 차후 validation 에러 추가

    @Before
    public void setting(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void 식재료_등록() throws Exception { // X

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientRegisterRequestDTO request = IngredientRegisterRequestDTO.builder()
                .name("감자")
                .expirationDate(LocalDate.now().plusDays(15))
                .capacity(30.0)
                .capacityUnit("g")
                .storageMethod("실온")
                .imageId(1)
                .build();

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_등록_실패() throws Exception { // X

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientRegisterRequestDTO request = IngredientRegisterRequestDTO.builder()
                .name("감자")
                .expirationDate(LocalDate.now().plusDays(15))
                .capacity(30.0)
                .capacityUnit("g")
                .storageMethod("방관")
                .imageId(1)
                .build();

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_수정() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientUpdateRequestDTO request = IngredientUpdateRequestDTO.builder()
                .expirationDate(LocalDate.now().plusDays(15))
                .capacity(30.0)
                .storageMethod("냉동")
                .build();

        Long id = testData.createIngredient("안심", "email123@gmail.com");

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(put("/api/ingredients/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_수정_실패() throws Exception { // X

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientUpdateRequestDTO request = IngredientUpdateRequestDTO.builder()
                .expirationDate(LocalDate.now().plusDays(15))
                .capacity(30.0)
                .storageMethod("방관")
                .build();

        Long id = testData.createIngredient("안심", "email123@gmail.com");

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(put("/api/ingredients/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_삭제() throws Exception { // O

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        Long id = testData.createIngredient("안심", "email123@gmail.com");

        mockMvc.perform(delete("/api/ingredients/" + id)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }
    
    @Test
    void 식재료_일괄_삭제() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        List<String> ingredients = new ArrayList<>(Arrays.asList("안심", "감자", "호박"));
        List<Long> ids = new ArrayList<>();

        for (String ingredient : ingredients) {
            ids.add(testData.createIngredient(ingredient, "email123@gmail.com"));
        }

        IngredientListRemoveRequestDTO request = IngredientListRemoveRequestDTO.builder()
                .removeIds(ids)
                .build();

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(delete("/api/ingredients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

}