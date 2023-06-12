package refrigerator.back.recipe.adapter.in.dto;

import lombok.*;
import refrigerator.back.global.image.Image;
import refrigerator.back.global.image.ImageGenerator;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InRecipeCourseDto extends Image {

    private String step;
    private String explanation;
    private String image;

    @Override
    public void generateImageUrl(ImageGenerator generator) {
        this.image = generator.getUrl(image);
    }
}
