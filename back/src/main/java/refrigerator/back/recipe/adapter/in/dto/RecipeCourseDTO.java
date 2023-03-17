package refrigerator.back.recipe.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCourseDTO {
    private String step;
    private String explanation;
    private String image;
}
