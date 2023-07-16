package refrigerator.back.ingredient.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import refrigerator.back.global.image.ImageGenerator;
import refrigerator.back.global.image.TestImageGenerator;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(SpringExtension.class)
//@TestPropertySource(properties = {"spring.config.location = classpath:application.yml"})
class IngredientDTOTest {

//    @Value("${s3.image.route}")
//    String route;
//    @Value("${s3.image.ingredient.path}")
//    String path;

    @Test
    @DisplayName("식재료 DTO 테스트")
    void ingredientDetailDTOTest() {

        LocalDate expirationDate = LocalDate.of(2023, 1, 1);

        IngredientDTO dto = IngredientDTO.builder()
                .ingredientID(1L)
                .expirationDate(expirationDate)
                .name("감자")
                .image("test.png")
                .build();

        assertThat(dto.getIngredientID()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("감자");
        assertThat(dto.getExpirationDate()).isEqualTo(expirationDate);
        assertThat(dto.getImage()).isEqualTo("test.png");

        // case 1 : now - expirationDate < 0 (유통기한 안지남)
        dto.calculateRemainDays(LocalDate.of(2022,12,31)); // now

        assertThat(dto.getRemainDays()).isEqualTo("-1");

        // case 1 : now - expirationDate == 0
        dto.calculateRemainDays(LocalDate.of(2023,1,1)); // now

        assertThat(dto.getRemainDays()).isEqualTo("0");

        // case 1 : now - expirationDate > 0 (유통기한 지남)
        dto.calculateRemainDays(LocalDate.of(2023,1,2)); // now

        assertThat(dto.getRemainDays()).isEqualTo("+1");

//        ImageGenerator imageGenerator = new TestImageGenerator(route, path);

//        dto.generateImageUrl(imageGenerator);
//
//        assertThat(route).isNotNull();
//        assertThat(path).isNotNull();
//        assertThat(dto.getImage()).isEqualTo(route + path + "test.png");

    }

}