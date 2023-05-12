package refrigerator.back.ingredient.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.in.RegisterIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.ReadIngredientPort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class LookUpIngredientServiceTest {

    @Autowired RegisterIngredientUseCase registerIngredientUseCase;
    @Autowired RemoveIngredientUseCase removeIngredientUseCase;
    @Autowired ReadIngredientPort readIngredientPort;
    @Autowired IngredientLookUpService ingredientLookUpService;

    @Test
    @DisplayName("식재료_이름에_따른_용량단위_반환")
    void getIngredientUnit() {
        RegisteredIngredient ingredient = ingredientLookUpService.getIngredient("당근");
        assertThat(ingredient.getUnit()).isEqualTo("g");
        assertThat(ingredient.getImage()).isEqualTo(9);
    }

    @Test
    @DisplayName("식재료 목록 조회")
    void getIngredientList() {

        List<String> names = new ArrayList<>(Arrays.asList(
                "당근", "고구마", "호박", "김치", "토란", "감자", "치즈", "미역",
                "쌀", "돼지고기", "소고기", "닭고기", "가지", "멸치", "버섯",
                "굴", "홍합", "가자미", "전복", "가리비", "고등어", "오징어",
                "쭈꾸미", "문어", "고추", "배추", "무", "달걀", "파프리카"));

        List<Long> ids = new ArrayList<>();
        Integer days = -14;

        for (String name : names) {
            ids.add(setIngredient(name, LocalDate.now().plusDays(days++), 100.0, "개", IngredientStorageType.FRIDGE, 1, "asd123@naver.com"));
        }

        // deadline check (false)

        Integer count1 = 0;

        for (int i = 0; i < 6; i++) {
            List<IngredientResponseDTO> ingredientList = ingredientLookUpService.getIngredientList(
                    new IngredientSearchCondition(IngredientStorageType.FRIDGE, false, "asd123@naver.com"), i, 5);

            count1 += ingredientList.size();
            for (IngredientResponseDTO dto : ingredientList) {
                log.info(dto.toString());
                assertThat(dto.getIngredientID()).isNotNull();
                assertThat(dto.getName()).isNotNull();
                assertThat(dto.getImage()).isNotNull();
                assertThat(dto.getRemainDays()).isNotNull();
            }
        }

        // deadline check (true)

        Integer count2 = 0;

        for (int i = 0; i < 3; i++) {
            List<IngredientResponseDTO> ingredientList = ingredientLookUpService.getIngredientList(
                    new IngredientSearchCondition(IngredientStorageType.FRIDGE, true, "asd123@naver.com"), i, 5);

            count2 += ingredientList.size();
            for (IngredientResponseDTO dto : ingredientList) {
                log.info(dto.toString());
                assertThat(dto.getIngredientID()).isNotNull();
                assertThat(dto.getName()).isNotNull();
                assertThat(dto.getImage()).isNotNull();
                assertThat(dto.getRemainDays()).isNotNull();
            }
        }

        assertThat(count1).isEqualTo(29);
        assertThat(count2).isEqualTo(14);

        // storage check

        List<String> namesByCold = new ArrayList<>(Arrays.asList("청양고추", "배추김치", "검정콩", "양고기"));
        List<Long> idList = new ArrayList<>();

        for (String name : namesByCold) {
            idList.add(setIngredient(name, LocalDate.now().plusDays(5), 30.0, "g", IngredientStorageType.FREEZER, 1, "asd123@naver.com"));
        }

        List<IngredientResponseDTO> list2 = ingredientLookUpService.getIngredientList(
                new IngredientSearchCondition(IngredientStorageType.FREEZER, false, "asd123@naver.com"), 0, 15);

        for (IngredientResponseDTO dto : list2) {
            log.info(dto.toString());
            assertThat(dto.getIngredientID()).isNotNull();
            assertThat(dto.getName()).isNotNull();
            assertThat(dto.getImage()).isNotNull();
            assertThat(dto.getRemainDays()).isNotNull();
        }

        assertThat(list2.size()).isEqualTo(4);

        // delete check

        removeIngredientUseCase.removeIngredient(readIngredientPort.getIngredientById(idList.get(1)).getId());
        removeIngredientUseCase.removeIngredient(readIngredientPort.getIngredientById(idList.get(2)).getId());

        assertThat(ingredientLookUpService.getIngredientList(
                new IngredientSearchCondition(IngredientStorageType.FREEZER, false, "asd123@naver.com"), 0, 15).size()).isEqualTo(2);

    }

    @Test
    @DisplayName("식재료 검색")
    void searchIngredient() {

        ArrayList<String> names = new ArrayList<>(Arrays.asList("당근", "고구마", "토란", "감자", "치즈", "쌀", "돼지고기"));

        List<Long> ids = setIngredientList(names, LocalDate.now().plusDays(10), 100.0, "g", 1, "asd123@naver.com");

        // delete check

        removeIngredientUseCase.removeIngredient(readIngredientPort.getIngredientById(ids.get(1)).getId());
        removeIngredientUseCase.removeIngredient(readIngredientPort.getIngredientById(ids.get(2)).getId());

        List<IngredientResponseDTO> ingredientNotDeleted = ingredientLookUpService.getIngredientListOfAll("asd123@naver.com");

        for (IngredientResponseDTO dto : ingredientNotDeleted) {
            log.info(dto.toString());
            assertThat(dto.getIngredientID()).isNotNull();
            assertThat(dto.getName()).isNotNull();
            assertThat(dto.getImage()).isNotNull();
            assertThat(dto.getRemainDays()).isNotNull();
        }

        assertThat(ingredientNotDeleted.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("식재료 단건 조회")
    void getIngredient() {

        Long id = setIngredient("돼지고기", LocalDate.now().plusDays(5),
                70.0, "g", IngredientStorageType.FREEZER, 1,"asd123@naver.com");

        IngredientDetailResponseDTO responseDTO = ingredientLookUpService.getIngredient(id);

        log.info(responseDTO.toString());
        assertThat(responseDTO.getIngredientID()).isEqualTo(id);
        assertThat(responseDTO.getName()).isEqualTo("돼지고기");
        assertThat(responseDTO.getStorage()).isEqualTo(IngredientStorageType.FREEZER);
        assertThat(responseDTO.getExpirationDate()).isEqualTo(LocalDate.now().plusDays(5));
        assertThat(responseDTO.getRemainDays()).isEqualTo(-5L);
        assertThat(responseDTO.getVolume()).isEqualTo(70);
        assertThat(responseDTO.getUnit()).isEqualTo("g");
        assertThat(responseDTO.getImage()).isEqualTo("IMAGE_INGREDIENT_가공식품.png");

        readIngredientPort.getIngredientById(id).delete();

        // 삭제된 식재료 조회
        assertThatThrownBy(() -> ingredientLookUpService.getIngredient(id))
                .isInstanceOf(BusinessException.class);

        // 없는 식재료 조회
        assertThatThrownBy(() -> readIngredientPort.getIngredientById(-1L))
                .isInstanceOf(BusinessException.class);;
    }

    @Test
    @DisplayName("임박 식재료 목록 조회")
    void getIngredientListByDeadline() {

        ArrayList<String> arrayList1 = new ArrayList<>(Arrays.asList("당근", "고구마", "호박", "김치"));
        ArrayList<String> arrayList2 = new ArrayList<>(Arrays.asList("토란", "감자", "치즈"));
        ArrayList<String> arrayList3 = new ArrayList<>(Arrays.asList("쌀", "돼지고기"));

        List<Long> ids1 = setIngredientList(arrayList1, LocalDate.now().plusDays(1), 20.0, "개", 1, "asd123@naver.com");
        List<Long> ids2 = setIngredientList(arrayList2, LocalDate.now().plusDays(3), 20.0, "개", 1, "asd123@naver.com");
        List<Long> ids3 = setIngredientList(arrayList3, LocalDate.now().plusDays(5), 20.0, "개", 1, "asd123@naver.com");

        List<List<IngredientResponseDTO>> ingredientListByDeadline = new ArrayList<>();

        removeIngredientUseCase.removeIngredient(ids1.get(1));
        removeIngredientUseCase.removeIngredient(ids2.get(1));
        removeIngredientUseCase.removeIngredient(ids3.get(1));

        ingredientListByDeadline.add(ingredientLookUpService.getIngredientListByDeadline(1L, "asd123@naver.com"));
        ingredientListByDeadline.add(ingredientLookUpService.getIngredientListByDeadline(3L, "asd123@naver.com"));
        ingredientListByDeadline.add(ingredientLookUpService.getIngredientListByDeadline(5L, "asd123@naver.com"));

        for (List<IngredientResponseDTO> ingredientResponseDTOS : ingredientListByDeadline) {
            for (IngredientResponseDTO dto : ingredientResponseDTOS) {
                log.info(dto.toString());
                assertThat(dto.getIngredientID()).isNotNull();
                assertThat(dto.getName()).isNotNull();
                assertThat(dto.getImage()).isNotNull();
                assertThat(dto.getRemainDays()).isNotNull();
            }
        }

        assertThat(ingredientListByDeadline.get(0).size()).isEqualTo(3);
        assertThat(ingredientListByDeadline.get(1).size()).isEqualTo(2);
        assertThat(ingredientListByDeadline.get(2).size()).isEqualTo(1);
    }


    List<Long> setIngredientList(List<String> names, LocalDate date, Double capacity, String unit, Integer image, String email) {

        List<Long> ids = new ArrayList<>();
        List<IngredientStorageType> method = new ArrayList<>(Arrays.asList(IngredientStorageType.FRIDGE, IngredientStorageType.FREEZER, IngredientStorageType.ROOM, IngredientStorageType.SEASON ));
        Random rand = new Random();

        for (String name : names) {
            int i = rand.nextInt(4);
            ids.add(setIngredient(name, date, capacity, unit, method.get(i), image, email));
        }
        return ids;
    }

    Long setIngredient(String name, LocalDate date, Double capacity, String unit, IngredientStorageType method, Integer image, String email) {
        return registerIngredientUseCase.registerIngredient(name, date, capacity, unit, method, image, email);
    }
}