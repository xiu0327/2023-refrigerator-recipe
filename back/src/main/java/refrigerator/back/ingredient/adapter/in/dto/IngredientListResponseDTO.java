package refrigerator.back.ingredient.adapter.in.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class IngredientListResponseDTO<T> {

    List<T> data;

    public IngredientListResponseDTO(List<T> data) {
        this.data = data;
    }
}
