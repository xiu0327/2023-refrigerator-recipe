package refrigerator.back.ingredient.adapter.in.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.BasicListResponseDTO;

import java.util.List;

@Data
@NoArgsConstructor
public class IngredientListResponseDTO extends BasicListResponseDTO<IngredientResponseDTO> {

    public IngredientListResponseDTO(List<IngredientResponseDTO> data) {
        super(data);
    }
}
