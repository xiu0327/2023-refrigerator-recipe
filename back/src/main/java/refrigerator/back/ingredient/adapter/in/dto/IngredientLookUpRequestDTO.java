package refrigerator.back.ingredient.adapter.in.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IngredientLookUpRequestDTO {

    String storage;
    boolean deadline;
}
