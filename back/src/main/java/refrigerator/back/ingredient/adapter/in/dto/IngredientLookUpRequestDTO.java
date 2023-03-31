package refrigerator.back.ingredient.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IngredientLookUpRequestDTO {

    String storage;
    boolean deadline;
}
