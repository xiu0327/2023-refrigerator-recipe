package refrigerator.back.ingredient.application.port.out;

import refrigerator.back.ingredient.adapter.in.dto.response.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientResponseDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;

import java.util.List;

public interface ReadIngredient {

    Ingredient findOne(Long id);

    List<IngredientResponseDTO> findList(String storage, boolean deadline, String email);

    List<IngredientResponseDTO> findSearchList(String name, String email);

    IngredientDetailResponseDTO findIngredient(Long id, String email);
}
