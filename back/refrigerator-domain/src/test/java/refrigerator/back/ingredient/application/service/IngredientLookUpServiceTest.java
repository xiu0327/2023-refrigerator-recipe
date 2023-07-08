package refrigerator.back.ingredient.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.time.CurrentDate;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientListPort;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientPort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientLookUpServiceTest {

    @InjectMocks IngredientLookUpService ingredientLookUpService;

    @Mock FindIngredientListPort findIngredientListPort;

    @Mock FindIngredientPort findIngredientPort;

    @Mock CurrentDate currentDate;

    @Test
    @DisplayName("식재료 목록 조회 테스트")
    void getIngredientListTest() {

        LocalDate now = LocalDate.of(2023,1,1);

        IngredientSearchCondition condition = IngredientSearchCondition.builder()
                .deadline(false)
                .storage(IngredientStorageType.FRIDGE)
                .email("email123@gmail.com")
                .build();

        IngredientDTO.IngredientDTOBuilder builder = IngredientDTO.builder()
                .remainDays(0)
                .image("test.png");

        IngredientDTO dto1 = builder.ingredientID(1L).name("감자").build();
        IngredientDTO dto2 = builder.ingredientID(2L).name("고구마").build();
        IngredientDTO dto3 = builder.ingredientID(3L).name("자색고구마").build();
        IngredientDTO dto4 = builder.ingredientID(4L).name("옥수수").build();

        List<IngredientDTO> ingredientDTOList = new ArrayList<>(List.of(dto1, dto2, dto3, dto4));

        given(currentDate.now())
                .willReturn(now);

        given(findIngredientListPort.getIngredientList(now, condition, 1, 10))
                .willReturn(ingredientDTOList);

        assertThat(ingredientLookUpService.getIngredientList(condition, 1, 10).size())
                .isEqualTo(4);
    }

    @Test
    @DisplayName("이메일에 따른 전체 식재료 조회 테스트")
    void getIngredientListOfAllTest() {

        String memberID = "email123@gmail.com";

        LocalDate now = LocalDate.of(2023,1,1);

        IngredientDTO.IngredientDTOBuilder builder = IngredientDTO.builder()
                .remainDays(0)
                .image("test.png");

        IngredientDTO dto1 = builder.ingredientID(1L).name("감자").build();
        IngredientDTO dto2 = builder.ingredientID(2L).name("고구마").build();
        IngredientDTO dto3 = builder.ingredientID(3L).name("자색고구마").build();
        IngredientDTO dto4 = builder.ingredientID(4L).name("옥수수").build();

        List<IngredientDTO> ingredientDTOList = new ArrayList<>(List.of(dto1, dto2, dto3, dto4));

        given(currentDate.now())
                .willReturn(now);

        given(findIngredientListPort.getIngredientListOfAll(now, memberID))
                .willReturn(ingredientDTOList);

        assertThat(ingredientLookUpService.getIngredientListOfAll(memberID).size())
                .isEqualTo(4);
    }

    @Test
    @DisplayName("임박 식재료 목록 조회 테스트")
    void getIngredientListByDeadlineTest() {

        LocalDate now = LocalDate.of(2023,1,1);
        String memberID = "email123@gmail.com";

        IngredientDTO.IngredientDTOBuilder builder = IngredientDTO.builder()
                .remainDays(0)
                .image("test.png");

        IngredientDTO dto1 = builder.ingredientID(1L).name("감자").build();
        IngredientDTO dto2 = builder.ingredientID(2L).name("고구마").build();
        IngredientDTO dto3 = builder.ingredientID(3L).name("자색고구마").build();
        IngredientDTO dto4 = builder.ingredientID(4L).name("옥수수").build();

        List<IngredientDTO> ingredientDTOList = new ArrayList<>(List.of(dto1, dto2, dto3, dto4));

        given(currentDate.now()).willReturn(now);

        given(findIngredientListPort.getIngredientListByDeadline(now, 1L, memberID))
                .willReturn(ingredientDTOList);

        assertThat(ingredientLookUpService.getIngredientListByDeadline(1L, memberID).size())
                .isEqualTo(4);
    }

    @Test
    @DisplayName("식재료 단건 조회 테스트")
    void getIngredientTest() {

        IngredientDetailDTO dto = IngredientDetailDTO.builder()
                .ingredientID(1L)
                .name("감자")
                .registrationDate(LocalDate.of(2023,1,1))
                .expirationDate(LocalDate.of(2023,1,1))
                .storage(IngredientStorageType.FRIDGE)
                .image("test.png")
                .volume(30.0)
                .unit("g")
                .remainDays(0)
                .build();

        LocalDate now = LocalDate.of(2023,1,1);

        given(currentDate.now())
                .willReturn(now);

        given(findIngredientPort.getIngredientDetail(now, 1L))
                .willReturn(dto);

        assertThat(ingredientLookUpService.getIngredient(1L)).isEqualTo(dto);
    }
}