package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.adapter.in.dto.InRecipeBasicListDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;

public interface FindRecipeListUseCase {
    InRecipeBasicListDTO<InRecipeDTO> getRecipeList(int page, int size);
}
