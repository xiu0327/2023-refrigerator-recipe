package refrigerator.back.ingredient.adapter.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.time.CurrentDate;
import refrigerator.back.global.time.TestCurrentDate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class OutIngredientDTOTest {

    @Test
    @DisplayName("식재료 outDto 테스트")
    void outIngredientDtoTest() {
        OutIngredientDTO dto = OutIngredientDTO.builder()
                .ingredientID(1L)
                .name("감자")
                .expirationDate(LocalDate.of(2023, 1, 1))
                .image("test.png")
                .build();

        assertThat(dto.getIngredientID()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("감자");
        assertThat(dto.getImage()).isEqualTo("test.png");
        assertThat(dto.getExpirationDate()).isEqualTo(LocalDate.of(2023, 1, 1));

        CurrentDate currentDate = TestCurrentDate.of(2023,1,1);

        assertThat(dto.getRemainDays()).isEqualTo("0");
    }
}