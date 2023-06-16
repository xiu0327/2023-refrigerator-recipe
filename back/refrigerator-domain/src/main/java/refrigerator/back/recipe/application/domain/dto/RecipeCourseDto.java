package refrigerator.back.recipe.application.domain.dto;

import lombok.*;
import refrigerator.back.global.image.Image;
import refrigerator.back.global.image.ImageGenerator;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCourseDto extends Image {

    private String step;
    private String explanation;
    private String image;

    @Override
    public void generateImageUrl(ImageGenerator generator) {
        this.image = generator.getUrl(image);
    }
}
