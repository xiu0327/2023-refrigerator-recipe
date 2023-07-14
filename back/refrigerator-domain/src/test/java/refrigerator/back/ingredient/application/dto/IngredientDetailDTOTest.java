package refrigerator.back.ingredient.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import refrigerator.back.global.image.ImageGenerator;
import refrigerator.back.global.image.TestImageGenerator;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// TODO : 차후 수정  => 지금 안됨.. @Value 값 가져올 수 있는 방법 찾아야함.

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"spring.config.location = classpath:application.yml"})
class IngredientDetailDTOTest {

    @Value("${s3.image.route}")
    String route;
    @Value("${s3.image.ingredient.path}")
    String path;

    @Test
    @DisplayName("식재료 상세 DTO 테스트")
    void ingredientDetailDTOTest() {
        IngredientDetailDTO dto = IngredientDetailDTO.builder()
                .ingredientID(1L)
                .name("감자")
                .storage(IngredientStorageType.FRIDGE)
                .registrationDate(LocalDate.of(2023, 1, 1))
                .expirationDate(LocalDate.of(2023, 1, 1))
                .remainDays(0)
                .volume(30.0)
                .unit("g")
                .image("test.png")
                .build();


        assertThat(dto.getIngredientID()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("감자");
        assertThat(dto.getStorage()).isEqualTo(IngredientStorageType.FRIDGE);
        assertThat(dto.getRegistrationDate()).isEqualTo(LocalDate.of(2023, 1, 1));
        assertThat(dto.getExpirationDate()).isEqualTo(LocalDate.of(2023, 1, 1));
        assertThat(dto.getRemainDays()).isEqualTo(0);
        assertThat(dto.getVolume()).isEqualTo(30.0);
        assertThat(dto.getUnit()).isEqualTo("g");
        assertThat(dto.getImage()).isEqualTo("test.png");

        ImageGenerator imageGenerator = new TestImageGenerator(route, path);

        dto.generateImageUrl(imageGenerator);

//        assertThat(route).isNotNull();
//        assertThat(path).isNotNull();
        assertThat(dto.getImage()).isEqualTo(route + path + "test.png");

    }
}