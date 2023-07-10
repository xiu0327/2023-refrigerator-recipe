package refrigerator.back.recipe.application.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCourseDto {

    private String step;
    private String explanation;
    private String image;
}
