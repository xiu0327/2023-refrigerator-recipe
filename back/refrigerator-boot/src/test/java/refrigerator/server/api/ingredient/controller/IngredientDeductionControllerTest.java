package refrigerator.server.api.ingredient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.port.out.ingredient.update.SaveIngredientPort;
import refrigerator.server.api.global.common.BasicListRequestDTO;
import refrigerator.server.api.ingredient.dto.IngredientDeductionRequestDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IngredientDeductionControllerTest {

    @Autowired MockMvc mockMvc;

    @Autowired
    SaveIngredientPort saveIngredientPort;

    @Test
    @DisplayName("식재료 차감")
    @WithUserDetails("jktest101@gmail.com")
    void ingredientDeductionTest() throws Exception {

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g");

        saveIngredientPort.saveIngredient(builder.name("콩나물").build());
        saveIngredientPort.saveIngredient(builder.name("안심").build());

        List<IngredientDeductionRequestDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient("콩나물", 60.0, "g"));
        list.add(createRecipeIngredient("안심", 60.0, "g"));

        BasicListRequestDTO<IngredientDeductionRequestDTO> dto = BasicListRequestDTO.<IngredientDeductionRequestDTO>builder()
                .data(list)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 차감 실패 : 회원 냉장고 비어있음")
    @WithUserDetails("jktest101@gmail.com")
    void ingredientDeductionTestFailEmptyRefrigerator() throws Exception {

        List<IngredientDeductionRequestDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient("콩나물", 60.0, "g"));
        list.add(createRecipeIngredient("안심", 60.0, "g"));

        BasicListRequestDTO<IngredientDeductionRequestDTO> dto = BasicListRequestDTO.<IngredientDeductionRequestDTO>builder()
                .data(list)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 차감 실패 : 빈 리스트")
    @WithUserDetails("jktest101@gmail.com")
    void ingredientDeductionTestFailEmptyList() throws Exception {

        BasicListRequestDTO<IngredientDeductionRequestDTO> dto = BasicListRequestDTO.<IngredientDeductionRequestDTO>builder()
                .data(new ArrayList<>())
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 차감 실패 : NULL 리스트")
    @WithUserDetails("jktest101@gmail.com")
    void ingredientDeductionTestFailEmptyNullList() throws Exception {

        BasicListRequestDTO<IngredientDeductionRequestDTO> dto = BasicListRequestDTO.<IngredientDeductionRequestDTO>builder()
                .data(null)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 차감 실패 : 리스트 내부 NULL")
    @WithUserDetails("jktest101@gmail.com")
    void ingredientDeductionTestFailEmptyNullListInner() throws Exception {

        List<IngredientDeductionRequestDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient(null, null, null));
        list.add(createRecipeIngredient("안심", 60.0, "g"));

        BasicListRequestDTO<IngredientDeductionRequestDTO> dto = BasicListRequestDTO.<IngredientDeductionRequestDTO>builder()
                .data(list)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 차감 실패 : 등록되어있지 않은 식재료")
    @WithUserDetails("jktest101@gmail.com")
    void ingredientDeductionTestFailUnknownIngredient() throws Exception {

        List<IngredientDeductionRequestDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient("파워에이드", 60.0, "g"));
        list.add(createRecipeIngredient("안심", 60.0, "g"));

        BasicListRequestDTO<IngredientDeductionRequestDTO> dto = BasicListRequestDTO.<IngredientDeductionRequestDTO>builder()
                .data(list)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 차감 실패 : 용량 음수값")
    @WithUserDetails("jktest101@gmail.com")
    void ingredientDeductionTestFailMinusUnit() throws Exception {
        
        List<IngredientDeductionRequestDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient("콩나물", -60.0, "g"));
        list.add(createRecipeIngredient("안심", 60.0, "g"));

        BasicListRequestDTO<IngredientDeductionRequestDTO> dto = BasicListRequestDTO.<IngredientDeductionRequestDTO>builder()
                .data(list)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 차감 실패 : 용량 초과")
    @WithUserDetails("jktest101@gmail.com")
    void ingredientDeductionTestFailOverUnit() throws Exception {

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g");

        saveIngredientPort.saveIngredient(builder.name("콩나물").build());
        saveIngredientPort.saveIngredient(builder.name("안심").build());
        
        List<IngredientDeductionRequestDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient("콩나물", 10000.0, "g"));
        list.add(createRecipeIngredient("안심", 60.0, "g"));

        BasicListRequestDTO<IngredientDeductionRequestDTO> dto = BasicListRequestDTO.<IngredientDeductionRequestDTO>builder()
                .data(list)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 차감 실패 : DTO 값 누락")
    @WithUserDetails("jktest101@gmail.com")
    void ingredientDeductionTestFailOmissionDTOValue() throws Exception {

        List<IngredientDeductionRequestDTO> list = new ArrayList<>();
        list.add(createRecipeIngredient("콩나물", 60.0, "g"));
        list.add(IngredientDeductionRequestDTO.builder().name("안심").build());

        BasicListRequestDTO<IngredientDeductionRequestDTO> dto = BasicListRequestDTO.<IngredientDeductionRequestDTO>builder()
                .data(list)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/ingredients/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }
    
    @Test
    @DisplayName("IngredientDtoCheck 검증")
    void IngredientDtoCheckTest() {

    }

    private IngredientDeductionRequestDTO createRecipeIngredient(String name, Double volume, String unit) {
        return IngredientDeductionRequestDTO.builder()
                .name(name)
                .volume(volume)
                .unit(unit).build();
    }
}