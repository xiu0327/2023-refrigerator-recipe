package refrigerator.back.ingredient.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.global.TestData;
import refrigerator.back.ingredient.adapter.in.dto.request.IngredientListRemoveRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.request.IngredientProposeRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.request.IngredientRegisterRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.request.IngredientUpdateRequestDTO;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
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

    @Before
    public void setting(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void 식재료_등록() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientRegisterRequestDTO request = IngredientRegisterRequestDTO.builder()
                .name("감자")
                .expirationDate(LocalDate.now().plusDays(15))
                .capacity(30.0)
                .storageMethod(IngredientStorageType.ROOM)
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
    void 식재료_등록_실패_NULL() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientRegisterRequestDTO request = IngredientRegisterRequestDTO.builder()
                .name(null)
                .expirationDate(null)
                .capacity(null)
                .storageMethod(null)
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
    void 식재료_등록_실패_DTO_값_누락() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientRegisterRequestDTO request = IngredientRegisterRequestDTO.builder()
                .name("감자")
                .storageMethod(IngredientStorageType.FREEZER)
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
    void 식재료_등록_실패_등록되지_않은_식재료() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientRegisterRequestDTO request = IngredientRegisterRequestDTO.builder()
                .name("파워에이드")
                .expirationDate(LocalDate.now().plusDays(15))
                .capacity(30.0)
                .storageMethod(IngredientStorageType.ROOM)
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
    void 식재료_등록_실패_용량_음수값() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientRegisterRequestDTO request = IngredientRegisterRequestDTO.builder()
                .name("감자")
                .expirationDate(LocalDate.now().plusDays(15))
                .capacity(-1.0)
                .storageMethod(IngredientStorageType.ROOM)
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
    void 식재료_등록_실패_용량_범위_초과() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientRegisterRequestDTO request = IngredientRegisterRequestDTO.builder()
                .name("감자")
                .expirationDate(LocalDate.now().plusDays(15))
                .capacity(10000.0)
                .storageMethod(IngredientStorageType.ROOM)
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
    void 식재료_등록_실패_존재하지_않는_보관타입() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        String content = "{\"name\":\"감자\",\"expirationDate\":\"2023-05-23\",\"capacity\":30.0,\"storageMethod\":\"방관\"}";

        mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    // validation 추가해야함.. (String or LocalDate)
    @Test
    void 식재료_등록_실패_지정되지_않은_날짜형식() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        String content = "{\"name\":\"감자\",\"expirationDate\":\"2023/05/23\",\"capacity\":30.0,\"storageMethod\":\"냉장\"}";

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
                .storageMethod(IngredientStorageType.FREEZER)
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
    void 식재료_수정_실패_NULL() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientUpdateRequestDTO request = IngredientUpdateRequestDTO.builder()
                .expirationDate(null)
                .capacity(null)
                .storageMethod(null)
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
    void 식재료_수정_실패_DTO_값_누락() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientUpdateRequestDTO request = IngredientUpdateRequestDTO.builder()
                .expirationDate(LocalDate.now().plusDays(15))
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
    void 식재료_수정_실패_용량_음수값() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientUpdateRequestDTO request = IngredientUpdateRequestDTO.builder()
                .expirationDate(LocalDate.now().plusDays(15))
                .capacity(-1.0)
                .storageMethod(IngredientStorageType.FREEZER)
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
    void 식재료_수정_실패_용량범위_초과() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientUpdateRequestDTO request = IngredientUpdateRequestDTO.builder()
                .expirationDate(LocalDate.now().plusDays(15))
                .capacity(10000.0)
                .storageMethod(IngredientStorageType.FREEZER)
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
    void 식재료_수정_실패_존재하지_않는_보관타입() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        Long id = testData.createIngredient("안심", "email123@gmail.com");

        String content = "{\"expirationDate\":\"2023-05-23\",\"capacity\":30.0,\"storageMethod\":\"방관\"}";

        mockMvc.perform(put("/api/ingredients/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    // validation 추가해야함.. (String or LocalDate)
    @Test
    void 식재료_수정_실패_지정되지_않은_날짜형식() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        Long id = testData.createIngredient("안심", "email123@gmail.com");

        String content = "{\"expirationDate\":\"2023/05/23\",\"capacity\":30.0,\"storageMethod\":\"냉동\"}";

        mockMvc.perform(put("/api/ingredients/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    // RequestParam and PathValuable validation 방법 강구

    @Test
    void 식재료_삭제() throws Exception {

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

        String content = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(delete("/api/ingredients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_일괄_삭제_실패_NULL() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientListRemoveRequestDTO request = IngredientListRemoveRequestDTO.builder()
                .removeIds(null)
                .build();

        String content = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(delete("/api/ingredients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_일괄_삭제_실패_값_누락() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientListRemoveRequestDTO request = IngredientListRemoveRequestDTO.builder()
                .build();

        String content = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(delete("/api/ingredients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_일괄_삭제_실패_빈_리스트() throws Exception {

        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientListRemoveRequestDTO request = IngredientListRemoveRequestDTO.builder()
                .removeIds(new ArrayList<>())
                .build();

        String content = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(delete("/api/ingredients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_요청() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientProposeRequestDTO dto = IngredientProposeRequestDTO.builder()
                .name("파워에이드")
                .capacityUnit("ml")
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/api/ingredients/propose")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_요청_실패_NULL() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientProposeRequestDTO dto = IngredientProposeRequestDTO.builder()
                .name(null)
                .capacityUnit(null)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/api/ingredients/propose")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_요청_실패_Blank() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientProposeRequestDTO dto = IngredientProposeRequestDTO.builder()
                .name(" ")
                .capacityUnit(" ")
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/api/ingredients/propose")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_요청_실패_DTO_값_누락() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        IngredientProposeRequestDTO dto = IngredientProposeRequestDTO.builder()
                .name("파워에이드")
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/api/ingredients/propose")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }
}