package refrigerator.back.ingredient.adapter.in.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IngredientListRemoveRequestDTO {

    List<Long> removeIds;
}
