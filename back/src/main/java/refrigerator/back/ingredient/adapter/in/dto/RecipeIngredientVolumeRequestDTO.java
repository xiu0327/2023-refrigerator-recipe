package refrigerator.back.ingredient.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientVolumeRequestDTO {
    List<RecipeIngredientVolumeDTO> ingredients;
}
