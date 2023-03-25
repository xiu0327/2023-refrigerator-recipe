package refrigerator.back.ingredient.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientResponseDTO;
import refrigerator.back.ingredient.adapter.out.persistence.IngredientAdapter;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class LookUpIngredientServiceTest {

    @Autowired
    IngredientService ingredientService;
    @Autowired
    IngredientAdapter ingredientAdapter;
    @Autowired
    LookUpIngredientService lookUpIngredientService;

    @BeforeEach
    void before() {
        ingredientService.register("당근", LocalDateTime.of(2022, 10, 1, 0, 0, 0),
                "10", "개", "냉장", "ehgus5825@naver.com");
        ingredientService.register("고구마", LocalDateTime.of(2021, 12, 2, 0, 0, 0),
                "20", "개", "실온", "ehgus5825@naver.com");
        ingredientService.register("토란", LocalDateTime.of(2022, 8, 3, 0, 0, 0),
                "30", "개", "실온", "ehgus5825@naver.com");
        ingredientService.register("감자", LocalDateTime.of(2023, 7, 4, 0, 0, 0),
                "40", "개", "실온", "ehgus5825@naver.com");
        ingredientService.register("치즈", LocalDateTime.of(2022, 6, 5, 0, 0, 0),
                "50", "장", "냉장", "ehgus5825@naver.com");
        ingredientService.register("쌀", LocalDateTime.of(2020, 5, 6, 0, 0, 0),
                "60", "g", "실온", "ehgus5825@naver.com");
        ingredientService.register("돼지고기", LocalDateTime.of(2023, 4, 7, 0, 0, 0),
                "70", "g", "냉동", "ehgus5825@naver.com");
    }

    @Test
    void 식재료_리스트_조회_BY_보관방식() {

    }

    @Test
    void 식재료_리스트_조회_BY_소비기한() {

    }

    @Test
    void 식재료_리스트_조회_BY_소비기한_보관방식 () {
        List<IngredientResponseDTO> DTOs = lookUpIngredientService.lookUpList("냉장", true, "ehgus5825@naver.com");
        for (IngredientResponseDTO dto : DTOs) {
            log.info(dto.toString());
        }
    }

    @Test
    void 식재료_검색 () {

    }

    @Test
    void 식재료_단건_조회 () {
        Long id = ingredientService.register("돼지고기", LocalDateTime.of(2023, 4, 7, 0, 0, 0),
                "70", "g", "냉동", "ehgus5825@naver.com");

        IngredientDetailResponseDTO responseDTO = lookUpIngredientService.lookUpDetail(id, "ehgus5825@naver.com");
        log.info(responseDTO.toString());
    }
}