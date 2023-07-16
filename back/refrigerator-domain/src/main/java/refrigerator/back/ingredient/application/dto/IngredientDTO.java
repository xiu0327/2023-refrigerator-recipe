package refrigerator.back.ingredient.application.dto;


import lombok.*;
import refrigerator.back.global.image.Image;
import refrigerator.back.global.image.ImageGenerator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IngredientDTO extends Image {

    private Long ingredientID;
    private String name;
    private LocalDate expirationDate;
    private String image;

    private String remainDays;

    @Override
    public void generateImageUrl(ImageGenerator generator) {
        this.image = generator.getUrl(image);
    }

    public void calculateRemainDays(LocalDate now) {
        long between = ChronoUnit.DAYS.between(this.expirationDate, now);

        this.remainDays = (between > 0) ? "+" + between : Long.toString(between);
    }
}
