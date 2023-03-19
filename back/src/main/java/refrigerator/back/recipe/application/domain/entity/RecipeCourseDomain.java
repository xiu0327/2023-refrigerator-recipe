package refrigerator.back.recipe.application.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class RecipeCourseDomain {
    private Long courseID;
    private Integer step;
    private String explanation;
    private String image;
}
