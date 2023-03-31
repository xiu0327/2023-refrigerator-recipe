package refrigerator.back.ingredient.adapter.in.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class IngredientListResponseDTO<T> {

    List<T> data;

    public IngredientListResponseDTO(List<T> data) {
        this.data = data;
    }
}
