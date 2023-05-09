package refrigerator.back.ingredient.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import refrigerator.back.ingredient.adapter.in.dto.request.RecipeIngredientVolumeDTO;
import refrigerator.back.ingredient.adapter.in.dto.request.RecipeIngredientVolumeRequestDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IngredientDeductionControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext context;
    @Autowired
    TestData testData;
    @Autowired
    CreateTokenPort createTokenPort;

    @Before
    public void setting(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void 식재료_차감() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        testData.createIngredient("콩나물", "email123@gmail.com");
        testData.createIngredient("안심", "email123@gmail.com");

        List<RecipeIngredientVolumeDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient("콩나물", 60.0, "g"));
        list.add(createRecipeIngredient("안심", 60.0, "g"));

        RecipeIngredientVolumeRequestDTO dto = RecipeIngredientVolumeRequestDTO.builder()
                .ingredients(list)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_차감_실패_회원_냉장고_비어있음() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        List<RecipeIngredientVolumeDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient("콩나물", 60.0, "g"));
        list.add(createRecipeIngredient("안심", 60.0, "g"));

        RecipeIngredientVolumeRequestDTO dto = RecipeIngredientVolumeRequestDTO.builder()
                .ingredients(list)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_차감_실패_빈_리스트() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        testData.createIngredient("콩나물", "email123@gmail.com");
        testData.createIngredient("안심", "email123@gmail.com");

        RecipeIngredientVolumeRequestDTO dto = RecipeIngredientVolumeRequestDTO.builder()
                .ingredients(new ArrayList<>())
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_차감_실패_NULL_리스트() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        testData.createIngredient("콩나물", "email123@gmail.com");
        testData.createIngredient("안심", "email123@gmail.com");

        RecipeIngredientVolumeRequestDTO dto = RecipeIngredientVolumeRequestDTO.builder()
                .ingredients(null)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_차감_실패_리스트_내부_NULL() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        testData.createIngredient("콩나물", "email123@gmail.com");
        testData.createIngredient("안심", "email123@gmail.com");

        List<RecipeIngredientVolumeDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient(null, null, null));
        list.add(createRecipeIngredient("안심", 60.0, "g"));

        RecipeIngredientVolumeRequestDTO dto = RecipeIngredientVolumeRequestDTO.builder()
                .ingredients(list)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_차감_실패_등록되어있지_않은_식재료() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        testData.createIngredient("콩나물", "email123@gmail.com");
        testData.createIngredient("안심", "email123@gmail.com");

        List<RecipeIngredientVolumeDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient("파워에이드", 60.0, "g"));
        list.add(createRecipeIngredient("안심", 60.0, "g"));

        RecipeIngredientVolumeRequestDTO dto = RecipeIngredientVolumeRequestDTO.builder()
                .ingredients(list)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_차감_실패_용량_음수값() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        testData.createIngredient("콩나물", "email123@gmail.com");
        testData.createIngredient("안심", "email123@gmail.com");

        List<RecipeIngredientVolumeDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient("콩나물", -60.0, "g"));
        list.add(createRecipeIngredient("안심", 60.0, "g"));

        RecipeIngredientVolumeRequestDTO dto = RecipeIngredientVolumeRequestDTO.builder()
                .ingredients(list)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_차감_실패_용량_초과() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        testData.createIngredient("콩나물", "email123@gmail.com");
        testData.createIngredient("안심", "email123@gmail.com");

        List<RecipeIngredientVolumeDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient("콩나물", 10000.0, "g"));
        list.add(createRecipeIngredient("안심", 60.0, "g"));

        RecipeIngredientVolumeRequestDTO dto = RecipeIngredientVolumeRequestDTO.builder()
                .ingredients(list)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    void 식재료_차감_실패_DTO_값_누락() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        testData.createIngredient("콩나물", "email123@gmail.com");
        testData.createIngredient("안심", "email123@gmail.com");

        List<RecipeIngredientVolumeDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient("콩나물", 60.0, "g"));
        list.add(RecipeIngredientVolumeDTO.builder().name("안심").build());

        RecipeIngredientVolumeRequestDTO dto = RecipeIngredientVolumeRequestDTO.builder()
                .ingredients(list)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header(HttpHeaders.AUTHORIZATION, testData.makeTokenHeader(token))
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    private RecipeIngredientVolumeDTO createRecipeIngredient(String name, Double volume, String unit) {
        return RecipeIngredientVolumeDTO.builder()
                .name(name)
                .volume(volume)
                .unit(unit).build();
    }
}