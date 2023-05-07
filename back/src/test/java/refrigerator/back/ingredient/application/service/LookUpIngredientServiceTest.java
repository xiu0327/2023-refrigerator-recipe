package refrigerator.back.ingredient.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.in.dto.IngredientRegisteredResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.out.persistence.IngredientAdapter;
import refrigerator.back.ingredient.adapter.out.repository.IngredientRepository;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;

import javax.persistence.EntityManager;
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

    @Autowired IngredientUpdateService ingredientService;
    @Autowired IngredientAdapter ingredientAdapter;
    @Autowired IngredientLookUpService ingredientLookUpService;
    @Autowired IngredientRepository ingredientRepository;
    @Autowired EntityManager entityManager;

    @Test
    void 식재료_목록_조회() {

        List<String> names = new ArrayList<>(Arrays.asList(
                "당근", "고구마", "호박", "김치", "토란", "감자", "치즈", "미역",
                "쌀", "돼지고기", "소고기", "닭고기", "가지", "멸치", "버섯",
                "굴", "홍합", "가자미", "전복", "가리비", "고등어", "오징어",
                "쭈꾸미", "문어", "고추", "배추", "무", "달걀", "파프리카"));

        List<Long> ids = new ArrayList<>();
        Integer days = -14;

        for (String name : names) {
            ids.add(setIngredient(name, LocalDate.now().plusDays(days++), 100.0, "개", "냉장", 1, "asd123@naver.com"));
        }

        // deadline check (false)

        Integer count1 = 0;

        for (int i = 0; i < 6; i++) {
            List<IngredientResponseDTO> ingredientList = ingredientLookUpService.getIngredientList(
                    new IngredientSearchCondition("냉장", false, "asd123@naver.com"), i, 5);

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
                    new IngredientSearchCondition("냉장", true, "asd123@naver.com"), i, 5);

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
            idList.add(setIngredient(name, LocalDate.now().plusDays(5), 30.0, "g", "냉동", 1, "asd123@naver.com"));
        }

        List<IngredientResponseDTO> list2 = ingredientLookUpService.getIngredientList(
                new IngredientSearchCondition("냉동", false, "asd123@naver.com"), 0, 15);

        for (IngredientResponseDTO dto : list2) {
            //log.info(dto.toString());
            assertThat(dto.getIngredientID()).isNotNull();
            assertThat(dto.getName()).isNotNull();
            assertThat(dto.getImage()).isNotNull();
            assertThat(dto.getRemainDays()).isNotNull();
        }

        assertThat(list2.size()).isEqualTo(4);

        // delete check

        ingredientService.removeIngredient(ingredientAdapter.getIngredientById(idList.get(1)).getId());
        ingredientService.removeIngredient(ingredientAdapter.getIngredientById(idList.get(2)).getId());

        assertThat(ingredientLookUpService.getIngredientList(
                new IngredientSearchCondition("냉동", false, "asd123@naver.com"), 0, 15).size()).isEqualTo(2);

    }

    @Test
    void 식재료_검색() {

        ArrayList<String> names = new ArrayList<>(Arrays.asList("당근", "고구마", "토란", "감자", "치즈", "쌀", "돼지고기"));

        List<Long> ids = setIngredientList(names, LocalDate.now().plusDays(10), 100.0, "g", 1, "asd123@naver.com");

        // delete check

        ingredientService.removeIngredient(ingredientAdapter.getIngredientById(ids.get(1)).getId());
        ingredientService.removeIngredient(ingredientAdapter.getIngredientById(ids.get(2)).getId());

        List<IngredientResponseDTO> ingredientNotDeleted = ingredientLookUpService.getIngredientListOfAll("asd123@naver.com");

        for (IngredientResponseDTO dto : ingredientNotDeleted) {
            // log.info(dto.toString());
            assertThat(dto.getIngredientID()).isNotNull();
            assertThat(dto.getName()).isNotNull();
            assertThat(dto.getImage()).isNotNull();
            assertThat(dto.getRemainDays()).isNotNull();
        }

        assertThat(ingredientNotDeleted.size()).isEqualTo(5);
    }

    @Test
    void 식재료_단건_조회() {

        Long id = setIngredient("돼지고기", LocalDate.now().plusDays(5),
                70.0, "g", "냉동", 1,"asd123@naver.com");

        IngredientDetailResponseDTO responseDTO = ingredientLookUpService.getIngredient(id);

        log.info(responseDTO.toString());
        assertThat(responseDTO.getIngredientID()).isEqualTo(id);
        assertThat(responseDTO.getName()).isEqualTo("돼지고기");
        assertThat(responseDTO.getStorage()).isEqualTo("냉동");
        assertThat(responseDTO.getExpirationDate()).isEqualTo(LocalDate.now().plusDays(5));
        assertThat(responseDTO.getRemainDays()).isEqualTo(-5L);
        assertThat(responseDTO.getVolume()).isEqualTo(70);
        assertThat(responseDTO.getUnit()).isEqualTo("g");
        assertThat(responseDTO.getImage()).isEqualTo(1);

        ingredientAdapter.getIngredientById(id).delete();

        // 삭제된 식재료 조회
        assertThatThrownBy(() -> ingredientLookUpService.getIngredient(id))
                .isInstanceOf(BusinessException.class);

        // 없는 식재료 조회
        assertThatThrownBy(() -> ingredientAdapter.getIngredientById(-1L))
                .isInstanceOf(BusinessException.class);;
    }

    @Test
    void 임박_식재료_목록_조회() {

        ArrayList<String> arrayList1 = new ArrayList<>(Arrays.asList("당근", "고구마", "호박", "김치"));
        ArrayList<String> arrayList2 = new ArrayList<>(Arrays.asList("토란", "감자", "치즈"));
        ArrayList<String> arrayList3 = new ArrayList<>(Arrays.asList("쌀", "돼지고기"));

        List<Long> ids1 = setIngredientList(arrayList1, LocalDate.now().plusDays(1), 20.0, "개", 1, "asd123@naver.com");
        List<Long> ids2 = setIngredientList(arrayList2, LocalDate.now().plusDays(3), 20.0, "개", 1, "asd123@naver.com");
        List<Long> ids3 = setIngredientList(arrayList3, LocalDate.now().plusDays(5), 20.0, "개", 1, "asd123@naver.com");

        List<List<IngredientResponseDTO>> ingredientListByDeadline = new ArrayList<>();

        ingredientService.removeIngredient(ids1.get(1));
        ingredientService.removeIngredient(ids2.get(1));
        ingredientService.removeIngredient(ids3.get(1));

        ingredientListByDeadline.add(ingredientLookUpService.getIngredientListByDeadline(1L, "asd123@naver.com"));
        ingredientListByDeadline.add(ingredientLookUpService.getIngredientListByDeadline(3L, "asd123@naver.com"));
        ingredientListByDeadline.add(ingredientLookUpService.getIngredientListByDeadline(5L, "asd123@naver.com"));

        for (List<IngredientResponseDTO> ingredientResponseDTOS : ingredientListByDeadline) {
            for (IngredientResponseDTO dto : ingredientResponseDTOS) {
                //log.info(dto.toString());
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
        List<String> method = new ArrayList<>(Arrays.asList("냉장", "냉동", "실온", "조미료"));
        Random rand = new Random();

        for (String name : names) {
            int i = rand.nextInt(4);
            ids.add(setIngredient(name, date, capacity, unit, method.get(i), image, email));
        }
        return ids;
    }

    Long setIngredient(String name, LocalDate date, Double capacity, String unit, String method, Integer image, String email) {
        return ingredientService.registerIngredient(name, date, capacity, unit, method, image, email);
    }
}