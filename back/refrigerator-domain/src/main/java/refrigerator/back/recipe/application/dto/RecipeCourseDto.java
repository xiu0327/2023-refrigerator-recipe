package refrigerator.back.recipe.application.dto;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
public class RecipeCourseDto {
    private String step;
    private String explanation;
    private String courseImage;
}
