package refrigerator.back.recipe.adapter.in.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InRecipeCourseDTO {
    private String step;
    private String explanation;
    private String image;
}
