package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.adapter.in.dto.InRecipeBasicListDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeCourseDTO;

public interface FindRecipeCourseUseCase {
    InRecipeBasicListDTO<InRecipeCourseDTO> getRecipeCourse(Long recipeID);
}
