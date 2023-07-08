package refrigerator.back.ingredient.application.dto;


import lombok.*;
import refrigerator.back.global.image.Image;
import refrigerator.back.global.image.ImageGenerator;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IngredientDTO extends Image {

    private Long ingredientID;
    private String name;
    private Integer remainDays;
    private String image;

    @Override
    public void generateImageUrl(ImageGenerator generator) {
        this.image = generator.getUrl(image);
    }
}
