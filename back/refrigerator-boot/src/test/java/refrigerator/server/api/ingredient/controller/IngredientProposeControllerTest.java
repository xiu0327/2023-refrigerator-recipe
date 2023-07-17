package refrigerator.server.api.ingredient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.server.api.ingredient.dto.IngredientProposeRequestDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IngredientProposeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("식재료 요청")
    @WithUserDetails("jktest101@gmail.com")
    void proposeIngredientTest() throws Exception {

        IngredientProposeRequestDTO dto = IngredientProposeRequestDTO.builder()
                .name("파워에이드")
                .unit("ml")
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/api/ingredients/propose")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 요청 실패 : NULL")
    @WithUserDetails("jktest101@gmail.com")
    void proposeIngredientTestFailNullValue() throws Exception {

        IngredientProposeRequestDTO dto = IngredientProposeRequestDTO.builder()
                .name(null)
                .unit(null)
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/api/ingredients/propose")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 요청 실패 : Blank")
    @WithUserDetails("jktest101@gmail.com")
    void proposeIngredientTestFailBlankValue() throws Exception {

        IngredientProposeRequestDTO dto = IngredientProposeRequestDTO.builder()
                .name(" ")
                .unit(" ")
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/api/ingredients/propose")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 요청 실패 : DTO 값 누락")
    @WithUserDetails("jktest101@gmail.com")
    void proposeIngredientTestFailOmissionDTOValue() throws Exception {

        IngredientProposeRequestDTO dto = IngredientProposeRequestDTO.builder()
                .name("파워에이드")
                .build();

        String content = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/api/ingredients/propose")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

}