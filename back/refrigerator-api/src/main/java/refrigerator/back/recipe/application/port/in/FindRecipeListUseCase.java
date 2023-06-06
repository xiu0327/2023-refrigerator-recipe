package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;

import java.util.List;

public interface FindRecipeListUseCase {
    List<InRecipeDTO> getRecipeList(int page, int size);
}
