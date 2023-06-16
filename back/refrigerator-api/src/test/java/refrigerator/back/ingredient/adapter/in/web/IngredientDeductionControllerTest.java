package refrigerator.back.ingredient.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.global.TestData;
import refrigerator.back.ingredient.adapter.in.dto.IngredientDeductionRequestDTO;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IngredientDeductionControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TestData testData;
    @Autowired
    CreateTokenPort createTokenPort;

    @Test
    void 식재료_차감() throws Exception {
        String email = testData.createMemberByEmail("email123@gmail.com");
        String token = createTokenPort.createTokenWithDuration(email, "ROLE_STEADY_STATUS", 3000);

        testData.createIngredient("콩나물", "email123@gmail.com");
        testData.createIngredient("안심", "email123@gmail.com");

        List<IngredientDeductionRequestDTO> list = new ArrayList<>();
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

        List<IngredientDeductionRequestDTO> list = new ArrayList<>();
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

        List<IngredientDeductionRequestDTO> list = new ArrayList<>();
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

        List<IngredientDeductionRequestDTO> list = new ArrayList<>();
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

        List<IngredientDeductionRequestDTO> list = new ArrayList<>();
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

        List<IngredientDeductionRequestDTO> list = new ArrayList<>();
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

        List<IngredientDeductionRequestDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient("콩나물", 60.0, "g"));
        list.add(IngredientDeductionRequestDTO.builder().name("안심").build());

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

    private IngredientDeductionRequestDTO createRecipeIngredient(String name, Double volume, String unit) {
        return IngredientDeductionRequestDTO.builder()
                .name(name)
                .volume(volume)
                .unit(unit).build();
    }
}