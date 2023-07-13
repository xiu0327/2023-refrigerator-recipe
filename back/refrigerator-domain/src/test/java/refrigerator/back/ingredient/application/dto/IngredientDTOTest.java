//package refrigerator.back.ingredient.application.dto;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import refrigerator.back.global.image.ImageGenerator;
//import refrigerator.back.global.image.TestImageGenerator;
//import refrigerator.back.ingredient.application.domain.IngredientStorageType;
//
//import java.time.LocalDate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//// TODO : 차후 수정  => 지금 안됨.. @Value 값 가져올 수 있는 방법 찾아야함.
//
//@ExtendWith(SpringExtension.class)
//@TestPropertySource(properties = {"spring.config.location = classpath:application.yml"})
//class IngredientDTOTest {
//
//    @Value("${s3.image.route}")
//    String route;
//    @Value("${s3.image.ingredient.path}")
//    String path;
//
//    @Test
//    @DisplayName("식재료 DTO 테스트")
//    void ingredientDetailDTOTest() {
//        IngredientDTO dto = IngredientDTO.builder()
//                .ingredientID(1L)
//                .name("감자")
//                .remainDays("0")
//                .image("test.png")
//                .build();
//
//        assertThat(dto.getIngredientID()).isEqualTo(1L);
//        assertThat(dto.getName()).isEqualTo("감자");
//        assertThat(dto.getRemainDays()).isEqualTo("0");
//        assertThat(dto.getRecipeImageName()).isEqualTo("test.png");
//
//        ImageGenerator imageGenerator = new TestImageGenerator(route, path);
//
//        dto.generateImageUrl(imageGenerator);
//
////        assertThat(route).isNotNull();
////        assertThat(path).isNotNull();
//        assertThat(dto.getRecipeImageName()).isEqualTo(route + path + "test.png");
//
//    }
//
//}