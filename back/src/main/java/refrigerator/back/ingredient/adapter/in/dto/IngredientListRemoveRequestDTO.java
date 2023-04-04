package refrigerator.back.ingredient.adapter.in.dto;


import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IngredientListRemoveRequestDTO {

    List<Long> removeIds;
}
