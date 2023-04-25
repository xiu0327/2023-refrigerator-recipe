//package refrigerator.back.ingredient.application.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import refrigerator.back.global.exception.BusinessException;
//import refrigerator.back.ingredient.adapter.in.dto.IngredientRegisteredResponseDTO;
//import refrigerator.back.ingredient.adapter.in.dto.IngredientResponseDTO;
//import refrigerator.back.ingredient.adapter.in.dto.IngredientDetailResponseDTO;
//import refrigerator.back.ingredient.adapter.out.persistence.IngredientAdapter;
//import refrigerator.back.ingredient.application.domain.Ingredient;
//import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//@Slf4j
//class LookUpIngredientServiceTest {
//
//    @Autowired
//    IngredientUpdateService ingredientService;
//    @Autowired
//    IngredientAdapter ingredientAdapter;
//    @Autowired
//    IngredientLookUpService ingredientLookUpService;
//
//    @BeforeEach
//    void before() {
//        ingredientService.registerIngredient("당근", LocalDate.of(2023, 3, 30),
//                10.0, "개", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("고구마", LocalDate.of(2023, 3, 24),
//                20.0, "개", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("호박", LocalDate.of(2023, 3, 24),
//                20.0, "개", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("김치", LocalDate.of(2023, 3, 24),
//                20, "개", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("토란", LocalDate.of(2023, 3, 25),
//                30, "개", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("감자", LocalDate.of(2023, 3, 26),
//                40, "개", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("치즈", LocalDate.of(2023, 3, 27),
//                50, "장", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("쌀", LocalDate.of(2023, 3, 28),
//                60, "g", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("돼지고기", LocalDate.of(2023, 3, 29),
//                70, "g", "냉장", 1,"ehgus5825@naver.com");
//    }
//
//    @Test
//    void 식재료_조회_test () {
//        ingredientService.registerIngredient("당근!!!!", LocalDate.of(2023, 3, 31),
//                10, "개", "냉장", 1, "ehgus5825@naver.com");
//        ingredientService.registerIngredient("고구마!!!!", LocalDate.of(2023, 4, 1),
//                20, "개", "냉장", 1, "ehgus5825@naver.com");
//        ingredientService.registerIngredient("토란!!!!", LocalDate.of(2023, 4, 2),
//                30, "개", "냉장", 1, "ehgus5825@naver.com");
//        ingredientService.registerIngredient("감자!!!!", LocalDate.of(2023, 4, 3),
//                40, "개", "냉장", 1, "ehgus5825@naver.com");
//        ingredientService.registerIngredient("치즈!!!", LocalDate.of(2023, 4, 4),
//                50, "장", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("쌀!!!!", LocalDate.of(2023, 4, 5),
//                60, "g", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("돼지고기!!!!", LocalDate.of(2023, 4, 6),
//                70, "g", "냉장", 1,"ehgus5825@naver.com");
//
//        for (int i = 0; i < 4; i++) {
//            List<IngredientResponseDTO> ingredientList = ingredientLookUpService.getIngredientList(new IngredientSearchCondition("냉장", false, "ehgus5825@naver.com"), i, 5);
//            for (IngredientResponseDTO dto : ingredientList) {
//                log.info(dto.toString());
//            }
//
//        }
//    }
//
//
//    @Test
//    void 식재료_검색() {
//        List<IngredientResponseDTO> ingredientListOfAll = ingredientLookUpService.getIngredientListOfAll("ehgus5825@naver.com");
//
//        for (IngredientResponseDTO dto : ingredientListOfAll) {
//            // log.info(dto.toString());
//            assertThat(dto.getId()).isNotNull();
//            assertThat(dto.getName()).isNotNull();
//            assertThat(dto.getImage()).isNotNull();
//            assertThat(dto.getRemainDays()).isNotNull();
//        }
//
//        assertThat(ingredientListOfAll.size()).isEqualTo(9);
//
//        Long ingredient1 = ingredientService.registerIngredient("당근!!!!", LocalDate.of(2022, 10, 1),
//                10, "개", "냉장", 1, "ehgus5825@naver.com");
//        Long ingredient2 = ingredientService.registerIngredient("고구마!!!!", LocalDate.of(2021, 12, 2),
//                20, "개", "실온", 1, "ehgus5825@naver.com");
//        Long ingredient3 = ingredientService.registerIngredient("토란!!!!", LocalDate.of(2022, 8, 3),
//                30, "개", "실온", 1, "ehgus5825@naver.com");
//        Long ingredient4 = ingredientService.registerIngredient("감자!!!!", LocalDate.of(2023, 7, 4),
//                40, "개", "실온", 1, "ehgus5825@naver.com");
//        ingredientService.registerIngredient("치즈!!!", LocalDate.of(2022, 6, 5),
//                50, "장", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("쌀!!!!", LocalDate.of(2020, 5, 6),
//                60, "g", "실온", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("돼지고기!!!!", LocalDate.of(2023, 4, 7),
//                70, "g", "냉동", 1,"ehgus5825@naver.com");
//
//        ingredientAdapter.getIngredientById(ingredient1).delete();
//        ingredientAdapter.getIngredientById(ingredient2).delete();
//        ingredientAdapter.getIngredientById(ingredient3).delete();
//        ingredientAdapter.getIngredientById(ingredient4).delete();
//
//        List<IngredientResponseDTO> ingredientNotDeleted = ingredientLookUpService.getIngredientListOfAll("ehgus5825@naver.com");
//
//        for (IngredientResponseDTO dto : ingredientNotDeleted) {
//            // log.info(dto.toString());
//            assertThat(dto.getId()).isNotNull();
//            assertThat(dto.getName()).isNotNull();
//            assertThat(dto.getImage()).isNotNull();
//            assertThat(dto.getRemainDays()).isNotNull();
//        }
//
//        assertThat(ingredientNotDeleted.size()).isEqualTo(12);
//    }
//
//    @Test
//    void 식재료_단건_조회() {
//        Long id = ingredientService.registerIngredient("돼지고기", LocalDate.of(2023, 4, 9),
//                70, "g", "냉동", 1,"ehgus5825@naver.com");
//
//        IngredientDetailResponseDTO responseDTO = ingredientLookUpService.getIngredient(id);
//        log.info(responseDTO.toString());
//        assertThat(responseDTO.getId()).isEqualTo(id);
//        assertThat(responseDTO.getName()).isEqualTo("돼지고기");
//        assertThat(responseDTO.getStorageMethod()).isEqualTo("냉동");
//        assertThat(responseDTO.getExpirationDate().toString()).isEqualTo("2023-04-09");
//        assertThat(responseDTO.getRemainDays()).isEqualTo(-2);
//        assertThat(responseDTO.getCapacity()).isEqualTo(70);
//        assertThat(responseDTO.getCapacityUnit()).isEqualTo("g");
//        assertThat(responseDTO.getImage()).isEqualTo("test.png");
//
//        Long id1 = ingredientService.registerIngredient("소고기", LocalDate.of(2023, 4, 6),
//                80, "g", "냉동", 1,"ehgus5825@naver.com");
//
//        Ingredient ingredient = ingredientAdapter.getIngredientById(id1);
//        ingredient.delete();
//
//        assertThatThrownBy(() -> ingredientLookUpService.getIngredient(id1))
//                .isInstanceOf(BusinessException.class);
//    }
//
//    @Test
//    void 식재료_목록_조회() {
//        ingredientService.registerIngredient("당근!!!!", LocalDate.of(2023, 3, 31),
//                10, "개", "냉장", 1, "ehgus5825@naver.com");
//        ingredientService.registerIngredient("고구마!!!!", LocalDate.of(2023, 4, 1),
//                20, "개", "냉장", 1, "ehgus5825@naver.com");
//        ingredientService.registerIngredient("토란!!!!", LocalDate.of(2023, 4, 2),
//                30, "개", "냉장", 1, "ehgus5825@naver.com");
//        ingredientService.registerIngredient("감자!!!!", LocalDate.of(2023, 4, 3),
//                40, "개", "냉장", 1, "ehgus5825@naver.com");
//        ingredientService.registerIngredient("치즈!!!", LocalDate.of(2023, 4, 4),
//                50, "장", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("쌀!!!!", LocalDate.of(2023, 4, 5),
//                60, "g", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("돼지고기!!!!", LocalDate.of(2023, 4, 6),
//                70, "g", "냉장", 1,"ehgus5825@naver.com");
//
//        IngredientSearchCondition condition1 = new IngredientSearchCondition("냉장", false, "ehgus5825@naver.com");
//
//        List<IngredientResponseDTO> list1 = ingredientLookUpService.getIngredientList(condition1, 0, 15);
//        for (IngredientResponseDTO dto : list1) {
//            log.info(dto.toString());
//            assertThat(dto.getId()).isNotNull();
//            assertThat(dto.getName()).isNotNull();
//            assertThat(dto.getImage()).isNotNull();
//            assertThat(dto.getRemainDays()).isNotNull();
//        }
//
//        assertThat(list1.size()).isEqualTo(15);
//
//        ingredientService.registerIngredient("청양고추", LocalDate.of(2023, 4, 3),
//                40, "개", "냉동", 1, "ehgus5825@naver.com");
//        ingredientService.registerIngredient("배추김치", LocalDate.of(2023, 4, 4),
//                50, "장", "냉동", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("검정콩", LocalDate.of(2023, 4, 5),
//                60, "g", "냉동", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("양고기", LocalDate.of(2023, 4, 6),
//                70, "g", "냉동", 1,"ehgus5825@naver.com");
//
//        IngredientSearchCondition condition2 = new IngredientSearchCondition("냉동", false, "ehgus5825@naver.com");
//
//        List<IngredientResponseDTO> list2 = ingredientLookUpService.getIngredientList(condition2, 0, 15);
//        for (IngredientResponseDTO dto : list2) {
//            log.info(dto.toString());
//            assertThat(dto.getId()).isNotNull();
//            assertThat(dto.getName()).isNotNull();
//            assertThat(dto.getImage()).isNotNull();
//            assertThat(dto.getRemainDays()).isNotNull();
//        }
//
//        assertThat(list2.size()).isEqualTo(4);
//
//        IngredientSearchCondition condition3 = new IngredientSearchCondition("냉장", true, "ehgus5825@naver.com");
//
//        List<IngredientResponseDTO> list3 = ingredientLookUpService.getIngredientList(condition3, 0, 15);
//        for (IngredientResponseDTO dto : list3) {
//            log.info(dto.toString());
//            assertThat(dto.getId()).isNotNull();
//            assertThat(dto.getName()).isNotNull();
//            assertThat(dto.getImage()).isNotNull();
//            assertThat(dto.getRemainDays()).isNotNull();
//        }
//
//        // assertThat(list3.size()).isEqualTo(9);
//    }
//
//    @Test
//    void 등록된_식재료_목록_조회() {
//        List<IngredientRegisteredResponseDTO> ingredientListOfRegistered = ingredientLookUpService.getIngredientListOfRegistered();
//        for (IngredientRegisteredResponseDTO dto : ingredientListOfRegistered) {
//            // log.info(dto.toString());
//            assertThat(dto.getId()).isNotNull();
//            assertThat(dto.getName()).isNotNull();
//            assertThat(dto.getUnit()).isNotNull();
//            // assertThat(dto.getImage()).isNotNull();
//        }
//
//        assertThat(ingredientListOfRegistered.size()).isEqualTo(506);
//    }
//
//    @Test
//    void NULL_테스트() {
//        assertThatThrownBy(() -> ingredientAdapter.getIngredientById(65L)).isInstanceOf(BusinessException.class);
//    }
//
//    @Test
//    void 임박_식재료_목록_조회() {
//        ingredientService.registerIngredient("당근", LocalDate.of(2023, 4, 8),
//                10, "개", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("고구마", LocalDate.of(2023, 4, 8),
//                20, "개", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("호박", LocalDate.of(2023, 4, 8),
//                20, "개", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("김치", LocalDate.of(2023, 4, 8),
//                20, "개", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("토란", LocalDate.of(2023, 4, 10),
//                30, "개", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("감자", LocalDate.of(2023, 4, 10),
//                40, "개", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("치즈", LocalDate.of(2023, 4, 10),
//                50, "장", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("쌀", LocalDate.of(2023, 4, 12),
//                60, "g", "냉장", 1,"ehgus5825@naver.com");
//        ingredientService.registerIngredient("돼지고기", LocalDate.of(2023, 4, 12),
//                70, "g", "냉장", 1,"ehgus5825@naver.com");
//
//        List<IngredientResponseDTO> ingredientListByDeadline1 = ingredientLookUpService.getIngredientListByDeadline(1L, "ehgus5825@naver.com");
//
//        for (IngredientResponseDTO dto : ingredientListByDeadline1) {
//            log.info(dto.toString());
//        }
//
//        List<IngredientResponseDTO> ingredientListByDeadline2 = ingredientLookUpService.getIngredientListByDeadline(3L, "ehgus5825@naver.com");
//
//        for (IngredientResponseDTO dto : ingredientListByDeadline2) {
//            log.info(dto.toString());
//        }
//
//        List<IngredientResponseDTO> ingredientListByDeadline3 = ingredientLookUpService.getIngredientListByDeadline(5L, "ehgus5825@naver.com");
//
//        for (IngredientResponseDTO dto : ingredientListByDeadline3) {
//            log.info(dto.toString());
//        }
//    }
//
//}