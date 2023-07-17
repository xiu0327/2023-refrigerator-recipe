package refrigerator.server.api.ingredient.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IngredientLookUpControllerTest {

    @Autowired MockMvc mockMvc;

    @Autowired TestEntityManager em;

    @Test
    @DisplayName("식재료 이름에 따른 용량단위 반환")
    @WithUserDetails("jktest101@gmail.com")
    void findIngredientUnitTest() throws Exception {

        String name = "감자";

        mockMvc.perform(
                get("/api/ingredients/unit?name=" + name)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 이름에 따른 용량단위 반환 실패 : 등록되지 않은 식재료")
    @WithUserDetails("jktest101@gmail.com")
    void findIngredientUnitTestFailUnregisteredIngredients() throws Exception {

        String name = "파워에이드";

        mockMvc.perform(
                get("/api/ingredients/unit?name=" + name)
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 목록 조회")
    @WithUserDetails("jktest101@gmail.com")
    void findIngredientListTest() throws Exception {

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        em.persist(ingredient);

        String storage = "냉장";
        String deadline = "true";

        mockMvc.perform(
                get("/api/ingredients?storage="+storage+"&deadline="+deadline+"&page=0")
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 목록 조회 실패 : 존재하지 않는 보관타입")
    @WithUserDetails("jktest101@gmail.com")
    void findIngredientListTestFailNotExistStorageType() throws Exception {

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        em.persist(ingredient);

        String storage = "방관";
        String deadline = "false";

        mockMvc.perform(
                get("/api/ingredients?storage="+storage+"&deadline="+deadline+"&page=0")
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 목록 조회 실패 : Boolean 타입 오류")
    @WithUserDetails("jktest101@gmail.com")
    void findIngredientListTestFailBooleanTypeError() throws Exception {

        Ingredient ingredient = Ingredient.builder()
                .name("안심")
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        em.persist(ingredient);

        String storage = "냉장";
        String deadline = "test";

        mockMvc.perform(
                get("/api/ingredients?storage="+storage+"&deadline="+deadline+"&page=0")
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 검색")
    @WithUserDetails("jktest101@gmail.com")
    void searchIngredientListTest() throws Exception {

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g");

        em.persist(builder.name("콩나물").storageMethod(IngredientStorageType.FRIDGE).build());
        em.persist(builder.name("안심").storageMethod(IngredientStorageType.FREEZER).build());

        mockMvc.perform(
                get("/api/ingredients/search")
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 단건 조회")
    @WithUserDetails("jktest101@gmail.com")
    void findIngredientTest() throws Exception {

        Ingredient ingredient = Ingredient.builder()
                .name("콩나물")
                .capacity(60.0)
                .email("jktest101@gmail.com")
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .deleted(false)
                .image(1)
                .capacityUnit("g")
                .build();

        Long id = em.persistAndGetId(ingredient, Long.class);

        mockMvc.perform(
                get("/api/ingredients/" + id)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    @DisplayName("식재료 단건 조회_실패 : 알 수 없는 id")
    @WithUserDetails("jktest101@gmail.com")
    void findIngredientTestFail() throws Exception {

        mockMvc.perform(
                get("/api/ingredients/51651656165")
        ).andExpect(status().is4xxClientError()
        ).andDo(print());
    }

    @Test
    @DisplayName("임박 식재료 목록 조회")
    @WithUserDetails("jktest101@gmail.com")
    void findIngredientListByDeadlineTest() throws Exception {

        String days = "1";

        mockMvc.perform(
                get("/api/ingredients/deadline/" + days)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

}