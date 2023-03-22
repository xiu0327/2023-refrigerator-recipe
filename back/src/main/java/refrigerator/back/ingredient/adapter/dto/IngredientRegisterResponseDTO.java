package refrigerator.back.ingredient.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRegisterResponseDTO {

    private Long id;      // 식재료 id

}
