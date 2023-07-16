package refrigerator.back.ingredient.adapter.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class OutIngredientDetailDTOTest {

    @Test
    @DisplayName("식재료 상세 outDto 테스트")
    void outIngredientDetailDtoTest() {
        OutIngredientDetailDTO dto = OutIngredientDetailDTO.builder()
                .ingredientID(1L)
                .name("감자")
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .volume(30.0)
                .unit("g")
                .storage(IngredientStorageType.FRIDGE)
                .image("test.png")
                .build();

        assertThat(dto.getIngredientID()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("감자");
        assertThat(dto.getVolume()).isEqualTo(30.0);
        assertThat(dto.getImage()).isEqualTo("test.png");
        assertThat(dto.getUnit()).isEqualTo("g");
        assertThat(dto.getRegistrationDate()).isEqualTo(LocalDate.of(2023, 1, 1));
        assertThat(dto.getExpirationDate()).isEqualTo(LocalDate.of(2023, 1, 1));
        assertThat(dto.getStorage()).isEqualTo(IngredientStorageType.FRIDGE);
    }
}