package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeBasicListDTO;

public interface FindRecipeListUseCase {
    InRecipeBasicListDTO<InRecipeDTO> getRecipeList(int page, int size);
}
